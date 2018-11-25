package com.example.benjo.bil_app_kotlin.ui.saved


import android.view.ActionMode
import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.coroutines.CoroutineContext


class SavedPresenter(val carRepository: CarRepository,
                     val adapter: SavedAdapter,
                     val savedVM: SavedViewModel) : SavedContract.Presenter, CoroutineScope {

    private val TAG = "SavedPresenter"
    private var mActionMode: ActionMode? = null
    private var jobTracker: Job = Job()
    private var checks = 0
    private lateinit var view: SavedContract.View

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun getListAdapter(): SavedAdapter = adapter


    override fun attachView(view: SavedContract.View) {
        this.view = view
    }

    @Subscribe
    override fun onEvent(event: SavedListEvent<EventData>) {
        when (event) {
            is SavedListEvent.OnShortClick -> onShortClick(event.data)
            is SavedListEvent.OnLongClick -> onLongClick(event.data)
            is SavedListEvent.OnDeleteAllClickFromView -> onDeleteAllClickFromView()
            is SavedListEvent.OnDeleteClickFromView -> onDeleteClickFromView()
            is SavedListEvent.OnDeleteAllClickFromActionMode -> onDeleteAllClickFromActionMode()
            is SavedListEvent.OnDeleteClickFromActionMode -> onDeleteClickFromActionMode()
        }
    }

    private fun onShortClick(data: EventData) {
        if (savedVM.isActionMode) onClickInActionMode(data.position)
        else {
            launch {
                val car = async(Dispatchers.IO) { carRepository.getCar(data.row.vin) }.await()
                if (car != null) view.showCar(data.row)
            }
        }
    }

    private fun onLongClick(data: EventData) {
        if (!savedVM.isActionMode) startActionMode()
        onClickInActionMode(data.position)
    }


    override fun onDeleteAllClickFromView() {
        if (savedVM.carList.isNotEmpty()) {
            startActionMode()
            selectAll()
            checks = savedVM.carList.size
            mActionMode?.title = checks.toString()
        } else {
            view.showEmptyListMessage()
        }
    }

    private fun selectAll() {
        for (row in savedVM.carList) row.isChecked = true
        adapter.setList(savedVM.carList)
    }

    private fun unSelectAll() {
        for (row in savedVM.carList) row.isChecked = false
        adapter.setList(savedVM.carList)
    }

    private fun isAllSelected(): Boolean {
        for (row in savedVM.carList) {
            if (!row.isChecked) return false
        }
        return true
    }

    private fun onDeleteClickFromView() {
        if (savedVM.carList.isNotEmpty()) startActionMode()
        else view.showEmptyListMessage()
    }

    override fun onDeleteAllClickFromActionMode() {
        if (savedVM.carList.isNotEmpty()) {
            checks = when (isAllSelected()) {
                true -> {
                    unSelectAll()
                    0
                }
                false -> {
                    selectAll()
                    savedVM.carList.size
                }
            }
            if(checks == 0) {
                mActionMode?.title = "välj bil"
            } else {
                mActionMode?.title = checks.toString()
            }
        }
    }

    override fun onDeleteClickFromActionMode() {
        launch {
            val listOfCars = savedVM.carList
            var nbrOfDeletedCars = 0

            if (!listOfCars.isEmpty()) {
                for (row in listOfCars) {
                    if (row.isChecked) {
                        async(Dispatchers.IO) { carRepository.deleteCar(row.vin) }.await()
                        nbrOfDeletedCars++
                    }
                }
                val listOfCarsDB = async(Dispatchers.IO) { carRepository.getAllCars() }.await()

                if (!listOfCarsDB.isNullOrEmpty()) {
                    savedVM.carList = CommonUtils().listToArrayList(listOfCarsDB)
                } else {
                    savedVM.carList.clear()
                }
                if (nbrOfDeletedCars > 0) view.showNumberOfDeletedCars(nbrOfDeletedCars)
            }

            mActionMode?.finish()
        }
    }

    override fun startActionMode() {
        if (!savedVM.isActionMode) {
            mActionMode = view.startActionMode()
            savedVM.isActionMode = true
            adapter.isActionMode = true
            mActionMode?.title = "välj bil"
            adapter.notifyDataSetChanged() // needs to be here otherwise wont show checkboxes when delete icon clicked
        }
    }

    private fun onClickInActionMode(position: Int) {
        val row = savedVM.carList[position]
        if (row.isChecked) {
            row.isChecked = false
            checks--
        } else {
            row.isChecked = true
            checks++
        }
        if(checks == 0) {
            mActionMode?.title = "välj bil"
        } else {
            mActionMode?.title = checks.toString()
        }
        adapter.setList(savedVM.carList)
    }


    override fun checkAll() {
        selectAll()
        checks = savedVM.carList.size
        mActionMode?.title = checks.toString()
    }

    override fun loadSavedCars() {
        launch {
            with(savedVM.carList) {
                if (isNotEmpty()) {
                    adapter.setList(this)
                    view.showToolbarIcons()
                } else {
                    val listOfCarData = async(Dispatchers.IO) { carRepository.getAllCars() }.await()
                    if (listOfCarData.isNotEmpty()) {
                        addAll(CommonUtils().listToArrayList(listOfCarData))
                        adapter.setList(this)
                        view.showToolbarIcons()
                    } else {
                        view.hideToolbarIcons()
                    }
                }
            }
        }
    }

    override fun unregister() {
        EventBus.getDefault().unregister(this)
    }

    override fun register() {
        EventBus.getDefault().register(this)
    }

    override fun onDestroyActionMode() {
        mActionMode = null
        savedVM.isActionMode = false
        adapter.isActionMode = false
        checks = 0
        adapter.setList(savedVM.carList)
        unSelectAll()
        if(savedVM.carList.isEmpty()) {
            view.hideToolbarIcons()
        }
    }


}