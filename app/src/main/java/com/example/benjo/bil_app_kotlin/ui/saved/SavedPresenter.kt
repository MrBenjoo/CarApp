package com.example.benjo.bil_app_kotlin.ui.saved


import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import kotlin.coroutines.CoroutineContext


class SavedPresenter(private val carRepository: CarRepository,
                     private val adapter: SavedAdapter,
                     private val savedVM: SavedViewModel,
                     private val MY_TAG: String = "SavedPresenter") : SavedContract.Presenter, CoroutineScope {

    private lateinit var view: SavedContract.View
    private var jobTracker: Job = Job()
    private var carsSelected = 0
    private var isActionMode = false

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    @Subscribe
    override fun onEvent(event: SavedListEvent<EventData>) {
        when (event) {
            is SavedListEvent.OnShortClick -> onShortClick(event.data)

            is SavedListEvent.OnLongClick -> onLongClick(event.data)

            is SavedListEvent.OnDeleteAllClickView -> onDeleteAllClickView()

            is SavedListEvent.OnDeleteClickView -> onDeleteClickView()

            is SavedListEvent.OnDeleteAllClickActionMode -> onDeleteAllClickActionMode()

            is SavedListEvent.OnDeleteClickActionMode -> onDeleteClickActionMode()
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

    override fun onDeleteAllClickView() {
        startActionMode()
        selectAll()
        carsSelected = savedVM.carList.size
        view.setActionModeTitle(carsSelected.toString())
    }

    private fun onDeleteClickView() {
        startActionMode()
    }

    override fun onDeleteAllClickActionMode() {
        carsSelected = when (isAllSelected()) {
            true -> {
                unSelectAll()
                0
            }
            false -> {
                selectAll()
                savedVM.carList.size
            }
        }
        view.setActionModeTitle(carsSelected.toString())
    }

    override fun onDeleteClickActionMode() {
        launch {
            val deletions = async { deleteCars() }.await()

            val listCarData = async(Dispatchers.IO) { carRepository.getAllCars() }.await()

            if (listCarData.isNotEmpty()) {
                savedVM.carList = CommonUtils().toArrayList(listCarData)
            } else {
                savedVM.carList.clear()
            }
            if (deletions > 0) view.showNumberOfDeletedCars(deletions)

            view.finishActionMode()
        }
    }

    private suspend fun showCarOnClick(data: EventData) {
        val carData = async(Dispatchers.IO) { carRepository.getCar(data.row.vin) }
        if (carData.await() != null) view.showCar(data.row)
    }

    private suspend fun deleteCars(): Int {
        var nbrOfDeletedCars = 0

        savedVM.carList.forEach { row ->
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
        val row = savedVM.carList[position]

        when {
            row.isChecked -> {
                row.isChecked = false
                carsSelected--
            }
            else -> {
                row.isChecked = true
                carsSelected++
            }
        }

        view.setActionModeTitle(carsSelected.toString())
        adapter.updatePosition(row, position)
    }

    override fun selectAllRows() {
        selectAll()
        carsSelected = savedVM.carList.size
        view.setActionModeTitle(carsSelected.toString())
    }

    override fun loadSavedCars() {
        when {
            /* data retrieved from "cache" */
            savedVM.carList.isNotEmpty() -> {
                adapter.setList(savedVM.carList)
                view.showToolbarIcons()
            }
            /* data retrieved from database */
            else -> {
                launch { getCarsFromDatabase() }
            }
        }
    }

    private suspend fun getCarsFromDatabase() {
        val listCarData = async(Dispatchers.IO) { carRepository.getAllCars() }.await()
        if (listCarData.isNotEmpty()) {
            val arrayListCarData = CommonUtils().toArrayList(listCarData)
            with(savedVM.carList) {
                addAll(arrayListCarData)
                adapter.setList(this)
            }
            view.showToolbarIcons()
        } else {
            view.hideToolbarIcons()
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
        adapter.setList(savedVM.carList)
        unSelectAll()
        if (savedVM.carList.isEmpty()) {
            view.hideToolbarIcons()
        }
    }

    override fun getListAdapter(): SavedAdapter = adapter

    override fun attachView(view: SavedContract.View) {
        this.view = view
    }


}