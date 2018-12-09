package com.example.benjo.bil_app_kotlin.ui.saved


import android.util.Log
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.coroutines.CoroutineContext


class SavedPresenter(private val carRepository: CarRepository,
                     private val adapter: SavedAdapter,
                     private val MY_TAG: String = "SavedPresenter") : SavedContract.Presenter, CoroutineScope {

    private lateinit var view: SavedContract.View
    private var jobTracker: Job = Job()
    private var carsSelected = 0
    private var isActionMode = false
    private var showActionModeIcons = false
    private lateinit var listCarData: ArrayList<CarData>

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    @Subscribe
    override fun onEvent(event: SavedListEvent<EventData>) {
        when (event) {
            is SavedListEvent.OnShortClick -> onShortClick(event.data)

            is SavedListEvent.OnLongClick -> onLongClick(event.data)

            is SavedListEvent.OnEditClick -> onEditClick()

            is SavedListEvent.OnSelectAllClick -> onSelectAllClick()

            is SavedListEvent.OnDeleteClick -> onDeleteClick()
        }
    }

    private fun onShortClick(data: EventData) {
        if (isActionMode) onClickInActionMode(data.position)
        else launch { showCarOnClick(data) }
    }

    private fun onLongClick(data: EventData) {
        if (!isActionMode) startActionMode()
        onClickInActionMode(data.position)
    }

    override fun onEditClick() {
        startActionMode()
        view.hideDeleteIcon()
        adapter.notifyDataSetChanged()
        view.setActionModeTitle(carsSelected.toString())
    }

    override fun onSelectAllClick() {
        carsSelected = when (isAllSelected()) {
            true -> {
                unCheckAll()
                view.hideDeleteIcon()
                0
            }
            false -> {
                selectAll()
                if (!view.isShowingDeleteIcon()) {
                    view.showDeleteIcon()
                }
                listCarData.size
            }
        }
        adapter.notifyDataSetChanged()
        view.setActionModeTitle(carsSelected.toString())
    }

    override fun onDeleteClick() {
        launch {
            val deletions = async { deleteCars() }.await()

            if (deletions > 0) {
                listCarData.clear()
                val listDatabase = async(Dispatchers.IO) { carRepository.getAllCars() }.await()

                if (listDatabase.isNotEmpty()) {
                    listCarData.addAll(CommonUtils().toArrayList(listDatabase))
                }
                view.finishActionMode()
                view.showNumberOfDeletedCars(deletions)
            }
        }
    }


    private suspend fun showCarOnClick(data: EventData) {
        val carData = async(Dispatchers.IO) { carRepository.getCar(data.row.vin) }
        if (carData.await() != null) view.showCar(data.row)
    }

    private suspend fun deleteCars(): Int {
        var nbrOfDeletedCars = 0
        listCarData.forEach { row ->
            when {
                row.isChecked -> {
                    async(Dispatchers.IO) { carRepository.deleteCar(row.vin) }.await()
                    nbrOfDeletedCars++
                }
            }
        }
        return nbrOfDeletedCars
    }

    override fun startActionMode() {
        if (!isActionMode) {
            view.startActionMode()
            isActionMode = true
            adapter.isActionMode = true
        }
    }

    private fun onClickInActionMode(position: Int) {
        Log.d("SavedPresenter", "onClickInActionMode")
        Log.d("SavedPresenter ", position.toString())
        val row = listCarData[position].deepCopy()

        when {
            row.isChecked -> {
                row.isChecked = false
                carsSelected--
                showActionModeIcons = false
            }
            else -> {
                row.isChecked = true
                carsSelected++
                showActionModeIcons = true
            }
        }

        listCarData.removeAt(position)
        listCarData.add(position, row)

        adapter.updateList(listCarData)
        view.setActionModeTitle(carsSelected.toString())

        if (showActionModeIcons) {
            if (!view.isShowingDeleteIcon()) {
                view.showDeleteIcon()
            }
        } else {
            listCarData.forEach { row -> if (row.isChecked) return }
            view.hideDeleteIcon()
        }
    }

    override fun selectAllRows() {
        selectAll()
        carsSelected = listCarData.size
        view.setActionModeTitle(carsSelected.toString())
    }

    override fun loadSavedCars() {
        /* val cacheList = savedVM.getList()
         when(!cacheList.isNullOrEmpty()) {
             true -> {
                 adapter.updateList(cacheList)
                 view.showToolbarIcons()
             }
             false -> {
                 Log.d(MY_TAG, "loadSavedCars() -> from database")
                 launch { getCarsFromDatabase() }
             }

             /*
             /* data retrieved from "cache" */
             !savedVM.getList().isNullOrEmpty() -> {
                 Log.d(MY_TAG, "loadSavedCars() -> from viewmodel")
                 adapter.updateList(savedVM.getList())
                 view.showToolbarIcons()
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
        val listDatabase = async(Dispatchers.IO) { carRepository.getAllCars() }.await()
        if (listDatabase.isNotEmpty()) {
            listCarData = CommonUtils().toArrayList(listDatabase)
            adapter.updateList(listCarData)
            view.showToolbarIcons()
        } else {
            view.hideToolbarIcons()
        }
    }

    private fun selectAll() {
        for (row in listCarData) row.isChecked = true
        adapter.updateList(listCarData)
    }

    private fun unCheckAll() {
        for (row in listCarData) row.isChecked = false
    }

    private fun isAllSelected(): Boolean {
        for (row in listCarData) {
            if (!row.isChecked) return false
        }
        return true
    }

    override fun unregister() {
        EventBus.getDefault().unregister(this)
    }

    override fun register() {
        EventBus.getDefault().register(this)
    }

    override fun onDestroyActionMode() {
        isActionMode = false
        adapter.isActionMode = false
        carsSelected = 0
        unCheckAll()
        adapter.updateList(listCarData)
        if (adapter.getListSize() == listCarData.size) {
            adapter.notifyDataSetChanged()
        }
        if (listCarData.isEmpty()) view.hideToolbarIcons()
    }

    override fun getListAdapter(): SavedAdapter = adapter

    override fun attachView(view: SavedContract.View) {
        this.view = view
    }


}