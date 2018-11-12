package com.example.benjo.bil_app_kotlin.ui.saved


import android.view.ActionMode
import com.example.benjo.bil_app_kotlin.data.model.EventData
import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.coroutines.CoroutineContext


class SavedPresenter(val carRepository: CarRepository,
                     val adapter: SavedAdapter) : SavedContract.Presenter, CoroutineScope {

    private val TAG = "SavedPresenter"
    private lateinit var view: SavedContract.View
    private var carList = arrayListOf<CarData>()
    private var isActionMode = false
    private var mActionMode: ActionMode? = null
    private var jobTracker: Job = Job()
    private var checks = 0
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun getListAdapter(): SavedAdapter = adapter

    init {
        EventBus.getDefault().register(this)
    }

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
        if (isActionMode) onClickInActionMode(data.position)
        else {
            launch {
                val car = async(Dispatchers.IO) { carRepository.getCar(data.row.vin) }.await()
                if (car != null) view.showCar(data.row)
            }
        }
    }

    private fun onLongClick(data: EventData) {
        if (!isActionMode) startActionMode()
        onClickInActionMode(data.position)
    }

    /* SavedView - deleteAll  */
    override fun onDeleteAllClickFromView() {
        if (carList.isNotEmpty()) {
            startActionMode()
            adapter.selectAll()
            checks = carList.size
            mActionMode?.title = checks.toString()
        } else {
            view.showEmptyListMessage()
        }
    }

    /* SavedView - delete  */
    private fun onDeleteClickFromView() {
        if (carList.isNotEmpty()) startActionMode()
        else view.showEmptyListMessage()
    }

    /* ActionMode - deleteAll */
    override fun onDeleteAllClickFromActionMode() {
        if (carList.isNotEmpty()) {
            checks = when (adapter.isAllSelected()) {
                true -> {
                    adapter.unSelectAll()
                    0
                }
                false -> {
                    adapter.selectAll()
                    carList.size
                }
            }
            mActionMode?.title = checks.toString()
        }
    }

    /* ActionMode - delete */
    override fun onDeleteClickFromActionMode() {
        launch {
            if (carList.isNotEmpty()) {
                async(Dispatchers.IO) { carRepository.deleteAll() }.await()
                // keep car list and database list in sync
                for (car in carList) {
                    async(Dispatchers.IO) { carRepository.insertCar(car) }.await()
                }
                val nbrOfDeletedCars = async(Dispatchers.IO) { carRepository.deleteCheckedCars() }.await()
                val cars = async(Dispatchers.IO) { carRepository.getAllCars() }.await()

                if (cars != null && cars.isNotEmpty()) carList = CommonUtils().listToArrayList(cars)
                else carList.clear()
                if (nbrOfDeletedCars > 0) view.showNumberOfDeletedCars(nbrOfDeletedCars)
            }
            mActionMode?.finish()
        }
    }

    override fun startActionMode() {
        if (!isActionMode) {
            mActionMode = view.startActionMode()
            isActionMode = true
            adapter.isActionMode = isActionMode
            mActionMode?.title = "0"
            adapter.notifyDataSetChanged() // needs to be here otherwise wont show checkboxes when delete icon clicked
        }
    }

    private fun onClickInActionMode(position: Int) = with(carList[position]) {
        if (isChecked) {
            isChecked = false
            checks--
        } else {
            isChecked = true
            checks++
        }
        mActionMode?.title = checks.toString()
        adapter.setList(carList)
    }


    override fun checkAll() {
        adapter.selectAll()
        checks = carList.size
        mActionMode?.title = checks.toString()
    }

    override fun loadSavedCars() {
        launch {
            val listOfCarData = async(Dispatchers.IO) { carRepository.getAllCars() }.await()
            if (listOfCarData != null && listOfCarData.isNotEmpty()) {
                carList = CommonUtils().listToArrayList(listOfCarData)
                adapter.setList(carList)
            }
        }
    }

    override fun onDestroyActionMode() {
        mActionMode = null
        isActionMode = false
        adapter.isActionMode = isActionMode
        checks = 0
        adapter.setList(carList)
        adapter.unSelectAll()
        adapter.notifyDataSetChanged()
    }


}