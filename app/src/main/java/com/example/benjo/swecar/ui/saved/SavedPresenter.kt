package com.example.benjo.swecar.ui.saved


import android.os.Handler
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.data.*
import com.example.benjo.swecar.data.db.model.CarData
import com.example.benjo.swecar.data.db.repository.CarRepository
import com.example.benjo.swecar.utils.toSearchResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.coroutines.CoroutineContext


class SavedPresenter(private val carRepository: CarRepository,
                     private val adapter: SavedAdapter,
                     private val viewModel: MainViewModel) : SavedContract.Presenter, CoroutineScope {

    private var view: SavedContract.View? = null
    private var jobTracker: Job = Job()
    private var carsSelected = 0
    private lateinit var listCarData: ArrayList<CarData>
    private val MY_TAG: String = "SavedPresenter"

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    @Subscribe
    override fun onEvent(event: SavedListEvent<RowEventData>) {
        when (event) {
            is SavedListEvent.OnShortClick -> onShortClick(event.data)
            is SavedListEvent.OnLongClick -> onLongClick(event.data)
            is SavedListEvent.OnEditClick -> onEditClick()
            is SavedListEvent.OnDeleteClick -> onDeleteClick()
            is SavedListEvent.OnSelectAllClick -> onSelectAllClick()
            is SavedListEvent.OnDestroy -> unBind()
        }
    }

    private fun onShortClick(data: RowEventData) {
        when (adapter.isActionMode) {
            true -> onClickInActionMode(data.position)
            false -> launch { showCarOnClick(data) }
        }
    }

    private fun onLongClick(data: RowEventData) {
        if (!adapter.isActionMode) startActionMode()
        onClickInActionMode(data.position)
    }

    private fun onClickInActionMode(position: Int) {
        val carDataEdited = editRowInActionMode(listCarData[position].deepCopy())
        listCarData.removeAt(position)
        listCarData.add(position, carDataEdited)
        adapter.updateList(listCarData)
        view?.setActionModeTitle(carsSelected.toString())
        updateDeleteIconVisibility(carDataEdited.isChecked)
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
     * When CarData is NOT checked (=false) -> if there are cars checked then return, else hide
     * Delete icon.
     */
    private fun updateDeleteIconVisibility(isChecked: Boolean) {
        when (isChecked) {
            true -> {
                val isVisible = view!!.isShowingDeleteIcon()
                if (isVisible.not()) view?.showDeleteIcon()
            }
            false -> {
                if (carsSelected > 0) return
                view?.hideDeleteIcon()
            }
        }
    }

    private fun onEditClick() {
        startActionMode()
        view?.hideDeleteIcon()
        adapter.notifyDataSetChanged()
        view?.setActionModeTitle(carsSelected.toString())
    }

    private fun onSelectAllClick() {
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

    private fun onDeleteClick() {
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

    private suspend fun showCarOnClick(data: RowEventData) {
        val carData = async(Dispatchers.IO) { carRepository.getCar(data.row.vin) }
        if (carData.await() != null) {
            viewModel.setSearchResponse(GsonBuilder().toSearchResponse(data.row.json))
            view?.navigateToTabs()
        }
    }

    override fun startActionMode() {
        if (!adapter.isActionMode) {
            view?.startActionMode()
            adapter.isActionMode = true
            adapter.notifyDataSetChanged()
        }
    }

    override fun loadSavedCars() {
        launch {
            val listDatabase = withContext(Dispatchers.IO) { carRepository.getAllCars().toArrayList() }
            if (listDatabase.isNotEmpty()) {
                listCarData = listDatabase
                adapter.updateList(listCarData)
                view?.showToolbarIcons()
            } else {
                view?.hideToolbarIcons()
            }
        }
    }

    override fun onDestroyActionMode() {
        adapter.isActionMode = false
        if (carsSelected > 0) listCarData.unCheckAll()
        carsSelected = 0
        adapter.notifyDataSetChanged() // needed to set default view in recyclerview
        adapter.updateList(listCarData)
        Handler().postDelayed({
            adapter.updateList(listCarData)
            if (listCarData.isEmpty()) view?.hideToolbarIcons()
        }, 1)
    }

    override fun getListAdapter(): SavedAdapter = adapter

    override fun attachView(view: SavedContract.View) {
        this.view = view
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

}