package com.example.benjo.swecar.ui.tab

import com.example.benjo.swecar.data.Result
import com.example.benjo.swecar.data.db.model.CarData
import com.example.benjo.swecar.data.db.repository.CarRepository
import com.example.benjo.swecar.data.network.ApiHelper
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.example.benjo.swecar.data.toCarData
import com.example.benjo.swecar.data.toCompareData
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.ui.compare.data.model.Compare
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class TabsPresenter(private val view: TabsContract.View,
                    private val carRepository: CarRepository,
                    private val apiHelper: ApiHelper,
                    private val mainViewModel: MainViewModel) : TabsContract.Presenter, CoroutineScope {

    private val TAG = "Presenter"
    private var jobTracker: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker


    override fun onEvent(event: TabsEvent<String>) {
        when (event) {
            is TabsEvent.OnSearch -> onSearch(event.reg)
            is TabsEvent.OnActionSave -> onActionSave()
            is TabsEvent.OnActionCompare -> onActionCompare()
            is TabsEvent.OnDestroy -> jobTracker.cancel()
        }
    }

    private fun onSearch(reg: String?) {
        launch {
            val searched = searchReg(reg)

            when (searched) {
                is Result.Value -> handleServerValue(searched.value)
                is Result.Error -> view.showExceptionError(searched.error)
            }
        }
    }

    private suspend fun searchReg(reg: String?): Result<Exception, Response<SearchResponse>> {
        return try {
            view.showProgress()
            Result.build { apiHelper.searchRegAsync(reg).await() }
        } catch (e: Exception) {
            Result.build { throw e }
        } finally {
            view.hideProgress()
        }
    }

    private fun handleServerValue(response: Response<SearchResponse>) {
        when (response.isSuccessful) { // 200..300 OK
            true -> processResponseBody(response.body())
            false -> handleResponseCodes(response.code())
        }
    }

    private fun processResponseBody(response: SearchResponse?) {
        when (response != null) {
            true -> {
                when (view.isComparing()) {
                    true -> navigateToCompareView(mainViewModel.getSearchResponse(), response)
                    false -> {
                        mainViewModel.setSearchResponse(response)
                        EventBus.getDefault().post(response)
                    }
                }
            }
            false -> view.showServerError()
        }
    }

    private fun navigateToCompareView(responseCarOne: SearchResponse, responseCarTwo: SearchResponse) {
        val compare = Compare(responseCarOne.toCompareData(), responseCarTwo.toCompareData())
        mainViewModel.setCompareData(compare)
        view.navigateToCompareView()
    }

    private fun handleResponseCodes(code: Int) {
        when (code) {
            in 400..500 -> view.showClientError()
            in 500..600 -> view.showServerError()
        }
    }

    private fun onActionCompare(): Boolean {
        TabsView.isComparing = !TabsView.isComparing
        when (view.isComparing()) {
            true -> view.showCompareMode()
            false -> view.hideCompareMode()
        }
        return true
    }

    private fun onActionSave(): Boolean {
        val carData = mainViewModel.getSearchResponse().toCarData()
        saveToDatabase(carData)
        return true
    }

    override fun onActionSaveFake(): Boolean {
        with(mainViewModel.getSearchResponse()) {
            val model = carInfo!!.basic?.data?.model!!
            val modelYear = carInfo.basic!!.data!!.model_year!!
            val type = carInfo.basic.data!!.type!!
            val json = GsonBuilder().create().toJson(this)
            for (i in 0..11) {
                saveToDatabase(CarData(i.toLong(), i.toString(), model, modelYear, type, i.toString()/*vin*/, json))
            }
        }
        return true
    }

    private fun saveToDatabase(car: CarData) = launch {
        val carDatabase = withContext(Dispatchers.IO) { carRepository.getCar(car.vin) }
        when (carDatabase) {
            null -> {
                withContext(Dispatchers.IO) { carRepository.insertCar(car) }
                val saved = withContext(Dispatchers.IO) { carRepository.getCar(car.vin) }
                if (saved != null) view.showTextCarSaved()
            }
            else -> view.showTextCarAlreadySaved()
        }
    }

}