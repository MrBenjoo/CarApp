package com.example.benjo.bil_app_kotlin.ui.tab

import android.util.Log
import com.example.benjo.bil_app_kotlin.App
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.Result
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.network.CarService
import com.example.benjo.bil_app_kotlin.data.toCarData
import com.example.benjo.bil_app_kotlin.data.toCompareData
import com.example.benjo.bil_app_kotlin.ui.comparing.Compare
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.*
import retrofit2.Response
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class TabsPresenter(private val view: TabsContract.ViewTabs,
                    private val carRepository: CarRepository,
                    private val carService: CarService) : TabsContract.TabsPresenter, CoroutineScope {

    private val TAG = "TabsPresenter"
    private var jobTracker: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun search(reg: String?) {
        launch {
            if (App.isConnected()) {
                searchReal(reg)
            }
        }
        //async { searchFake(reg) }.await()
    }

    private suspend fun searchReal(reg: String?) {
        val searched = registrationSearch(reg)

        when (searched) {
            is Result.Value -> handleServerValue(searched.value)
            is Result.Error -> handleError(searched.error)
        }
    }

    private suspend fun registrationSearch(reg: String?): Result<Exception, Response<SearchResponse>> {
        return try {
            view.setProgressVisible()
            Result.build { carService.searchReg(reg).await() }
        } catch (e: Exception) {
            Result.build { throw e }
        } finally {
            view.setProgressInvisible()
        }
    }

    private fun handleServerValue(response: Response<SearchResponse>) {
        when (response.isSuccessful) { // 200..300 OK
            true -> processResponseBody(response.body())
            false -> handleResponseCodes(response.code())
        }
    }

    private fun handleError(error: Exception) {
        view.showExceptionError(error)
    }

    private fun processResponseBody(response: SearchResponse?) {
        when (response != null) {
            true -> {
                when (view.isComparing()) {
                    true -> navigateToCompareView(view.getResponseCarOne()!!, response)
                    false -> view.updateResult(response)
                }
            }
            false -> {
                Log.d(TAG, "processResponseBody -> body == null")
            }
        }
    }

    private fun navigateToCompareView(responseCarOne: SearchResponse, responseCarTwo: SearchResponse) {
        view.closeCompareSearch()

        /*val responseCarOne = view.getResponseCarOne()

        val gson = GsonBuilder().create()
        val jsonCarOne = gson.toJson(responseCarOne)
        val jsonCarTwo = gson.toJson(responseCarTwo)

        Compare(responseCarOne?.toCompareData(), responseCarTwo.toCompareData())

        val action = TabsViewDirections.actionTabsFragmentToMenuFragment(jsonCarOne, jsonCarTwo)

        view.navigateToCompareView(action)
        */

        val compare = Compare(responseCarOne.toCompareData(), responseCarTwo.toCompareData())
        view.navigateToCompareView(compare)

    }

    private fun handleResponseCodes(code: Int) {
        when (code) {
            in 400..500 -> view.showClientError()
            in 500..600 -> view.showServerError()
        }
    }

    override fun onActionCompare(): Boolean {
        TabsView.isComparing = !TabsView.isComparing
        when (view.isComparing()) {
            true -> view.compareModeSelected()
            false -> view.compareModeUnselected()
        }
        return true
    }

    override fun onActionSave(): Boolean {
        val carData = view.getResponseCarOne()!!.toCarData()
        saveToDatabase(carData)
        return true
    }

    override fun onActionSaveFake(searchResponse: SearchResponse?): Boolean {
        with(searchResponse?.carInfo!!) {
            val model = basic?.data?.model!!
            val modelYear = basic.data.model_year!!
            val type = basic.data.type!!
            val json = GsonBuilder().create().toJson(searchResponse)
            for (i in 0..11) {
                saveToDatabase(CarData(i.toLong(), i.toString(), model, modelYear, type, i.toString()/*vin*/, json))
            }
        }
        return true
    }

    private fun saveToDatabase(car: CarData) = launch {
        val carDatabase = withContext(Dispatchers.IO) { carRepository.getCar(car.vin) }
        if (carDatabase == null) {
            withContext(Dispatchers.IO) { carRepository.insertCar(car) }
            val saved = withContext(Dispatchers.IO) { carRepository.getCar(car.vin) }
            if (saved != null) view.showTextCarSaved()
        } else view.showTextCarAlreadySaved()
    }


    override fun cancelJob() {
        jobTracker.cancel()
    }

    private fun searchFake(reg: String?) = launch {
        view.setProgressVisible()
        val randomInt = Random.nextInt(3) + 1 // between 0 (inclusive) and n (exclusive)
        val jsonFileName = "bil_" + randomInt.toString() + ".json"
        try {
            val result = GsonBuilder().create().fromJson(CommonUtils().loadJSONFromAsset(
                    view.getContext(),
                    jsonFileName), SearchResponse::class.java)

            if (result != null) {
                if (view.isComparing()) {
                    //navigateToCompareView(result)
                } else {
                    view.updateResult(result)
                }
            } else {
                Log.d(TAG, "searchFake($reg) -> result == null")
            }
        } catch (jsonException: JsonSyntaxException) {
            Log.d(TAG, "jsonException")
            view.closeCompareSearch()
            view.showText(jsonException.message)
        }
        view.setProgressInvisible()
    }

}