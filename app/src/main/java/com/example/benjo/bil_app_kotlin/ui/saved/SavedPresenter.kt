package com.example.benjo.bil_app_kotlin.ui.saved


import android.os.Handler
import com.example.benjo.bil_app_kotlin.data.*
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepository
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.coroutines.CoroutineContext


class SavedPresenter(private val carRepository: CarRepository,
                     private val adapter: SavedAdapter) : SavedContract.Presenter, CoroutineScope {

    private var view: SavedContract.View? = null
    private var jobTracker: Job = Job()
    private var carsSelected = 0
    private var isActionMode = false
    private lateinit var listCarData: ArrayList<CarData>
    private val MY_TAG: String = "SavedPresenter"

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    @Subscribe
    override fun onEvent(event: SavedListEvent<EventData>) {
        when (event) {
            is SavedListEvent.OnShortClick -> onShortClick(event.data)
            is SavedListEvent.OnLongClick -> onLongClick(event.data)
            is SavedListEvent.OnEditClick -> onEditClick()
            is SavedListEvent.OnDeleteClick -> onDeleteClick()
            is SavedListEvent.OnSelectAllClick -> onSelectAllClick()
        }
    }

    private fun onShortClick(data: EventData) {
        when (isActionMode) {
            true -> onClickInActionMode(data.position)
            false -> launch { showCarOnClick(data) }
        }
    }

    private fun onLongClick(data: EventData) {
        if (!isActionMode) startActionMode()
        onClickInActionMode(data.position)
    }

    private fun onClickInActionMode(position: Int) {
        val editedCarData = editRowInActionMode(listCarData[position].deepCopy())

        listCarData.removeAt(position)
        listCarData.add(position, editedCarData)
        adapter.updateList(listCarData)

        view?.setActionModeTitle(carsSelected.toString())

        updateDeleteIconVisibility(editedCarData)
    }

    private fun editRowInActionMode(deepCopy: CarData): CarData {
        when (deepCopy.isChecked) {
            true -> {
                deepCopy.isChecked = false
                carsSelected--
            }
            false -> {
                deepCopy.isChecked = true
                carsSelected++
            }
        }
        return deepCopy
    }

    /**
     * When CarData is checked (=true) -> show Delete icon only if the icon is not already visible.
     * When CarData is NOT checked (=false) -> loop through list, if CarData is checked then return,
     * else hide Delete icon.
     */
    private fun updateDeleteIconVisibility(editedCarData: CarData) {
        when (editedCarData.isChecked) {
            true -> {
                if (!view!!.isShowingDeleteIcon()) {
                    view?.showDeleteIcon()
                }
            }
            false -> {
                listCarData.forEach { row -> if (row.isChecked) return }
                view?.hideDeleteIcon()
            }
        }
    }

    override fun onEditClick() {
        startActionMode()
        view?.hideDeleteIcon()
        adapter.notifyDataSetChanged()
        view?.setActionModeTitle(carsSelected.toString())
    }

    override fun onSelectAllClick() {
        carsSelected = when (listCarData.isAllSelected()) {
            true -> {
                listCarData.unCheckAll()
                view?.hideDeleteIcon()
                0
            }
            false -> {
                listCarData.selectAll()
                if (!view!!.isShowingDeleteIcon()) {
                    view?.showDeleteIcon()
                }
                listCarData.size
            }
        }
        adapter.notifyDataSetChanged()
        view?.setActionModeTitle(carsSelected.toString())
    }

    override fun onDeleteClick() {
        launch {
            val deletions = withContext(Dispatchers.Default) { deleteCars() }

            if (deletions > 0) {
                listCarData.clear()
                val listDatabase = withContext(Dispatchers.IO) { carRepository.getAllCars().toArrayList() }

                if (listDatabase.isNotEmpty()) {
                    listCarData.addAll(listDatabase)
                }
                view?.finishActionMode()
                view?.showNumberOfDeletedCars(deletions)
            }
        }
    }

    private suspend fun showCarOnClick(data: EventData) {
        val carData = async(Dispatchers.IO) { carRepository.getCar(data.row.vin) }
        if (carData.await() != null) view?.showCar(data.row)
    }

    private suspend fun deleteCars(): Int {
        var nbrOfDeletedCars = 0
        listCarData.forEach { row ->
            when {
                row.isChecked -> {
                    withContext(Dispatchers.IO) { carRepository.deleteCar(row.vin) }
                    nbrOfDeletedCars++
                }
            }
        }
        return nbrOfDeletedCars
    }

    override fun startActionMode() {
        if (!isActionMode) {
            view?.startActionMode()
            isActionMode = true
            adapter.isActionMode = true
            adapter.notifyDataSetChanged()
        }
    }

    override fun selectAllRows() {
        listCarData.selectAll()
        carsSelected = listCarData.size
        view?.setActionModeTitle(carsSelected.toString())
    }

    override fun loadSavedCars() {
        /* val cacheList = savedVM.getList()
         when(!cacheList.isNullOrEmpty()) {
             true -> {
                 adapter.updateList(cacheList)
                 view?.showToolbarIcons()
             }
             false -> {
                 Log.d(MY_TAG, "loadSavedCars() -> from database")
                 launch { getCarsFromDatabase() }
             }

             /*
             /* data retrieved from "cache" */
             !savedVM.getList().isNullOrEmpty() -> {
                 Log.d(MY_TAG, "loadSavedCars() -> from view?model")
                 adapter.updateList(savedVM.getList())
                 view?.showToolbarIcons()
             }
             /* data retrieved from database */
             else -> {
                 Log.d(MY_TAG, "loadSavedCars() -> from database")
                 launch { getCarsFromDatabase() }
             }
             */
         }*/

        launch { getCarsFromDatabase() }
    }

    private suspend fun getCarsFromDatabase() {
        val listDatabase = withContext(Dispatchers.IO) { carRepository.getAllCars().toArrayList() }
        if (listDatabase.isNotEmpty()) {
            listCarData = listDatabase
            adapter.updateList(listCarData)
            view?.showToolbarIcons()
        } else {
            view?.hideToolbarIcons()
        }
    }

    override fun unregister() {
        EventBus.getDefault().unregister(this)
    }

    override fun unBind() {
        jobTracker.cancel()
        view = null
    }

    override fun register() {
        EventBus.getDefault().register(this)
    }

    override fun onDestroyActionMode() {
        isActionMode = false
        adapter.isActionMode = false
        carsSelected = 0
        listCarData.unCheckAll()
        adapter.notifyDataSetChanged()
        Handler().postDelayed({
            adapter.updateList(listCarData)
            if (listCarData.isEmpty()) view?.hideToolbarIcons()
        }, 1)
    }

    override fun getListAdapter(): SavedAdapter = adapter

    override fun attachView(view: SavedContract.View) {
        this.view = view
    }

}