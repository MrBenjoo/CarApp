package com.example.benjo.bil_app_kotlin.ui.tab

import android.util.Log
import com.example.benjo.bil_app_kotlin.data.network.model.Result
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.network.CarService
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.*
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
        Log.d(TAG, "search($reg) -> before searchReal($reg)")
        launch {
            async { searchReal(reg) }.await()
            //async { searchFake(reg) }.await()
        }
    }

    private suspend fun searchReal(reg: String?): Result? {
        var result: Result? = null

        val response = carService.searchReg(reg).await()
        if (response.isSuccessful) {
            result = response.body()
            if (result != null) {
                if (response.raw().cacheResponse() != null) {
                    Log.d(TAG, "cache response")
                }
                if (response.raw().networkResponse() != null) {
                    Log.d(TAG, "network response")
                }

                if (view.isComparing()) {
                    view.navigateToCompareView(result)
                } else {
                    view.updateResult(result)
                }
            } else Log.d(TAG, "searchReal($reg) -> result == null")
        } else view.showResponseCode(response.code())
        return result
    }

    private fun searchFake(reg: String?) = launch {
        view.setProgressVisible()
        val randomInt = Random.nextInt(3) + 1 // between 0 (inclusive) and n (exclusive)
        val jsonFileName = "bil_" + randomInt.toString() + ".json"
        try {
            val result = GsonBuilder().create().fromJson(CommonUtils().loadJSONFromAsset(
                    view.getContext(),
                    jsonFileName), Result::class.java)

            if (result != null) {
                if (view.isComparing()) {
                    view.navigateToCompareView(result)
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

    private fun saveToDatabase(car: CarData) = launch {
        val carDatabase = async(Dispatchers.IO) { carRepository.getCar(car.vin) }.await()
        if (carDatabase == null) {
            async(Dispatchers.IO) { carRepository.insertCar(car) }.await()
            val saved = async(Dispatchers.IO) { carRepository.getCar(car.vin) }.await()
            if (saved != null) view.showMsgCarSaved()
        } else view.showMsgCarAlreadySaved()
    }


    override fun onImgSaveClicked(result: Result?) {
        with(result?.carInfo!!) {
            val reg = attributes?.regno!!
            val model = basic?.data?.model!!
            val modelYear = basic.data.model_year!!
            val type = basic.data.type!!
            val vin = attributes.vin!!
            val json = GsonBuilder().create().toJson(result)
            for (i in 0..11) {
                saveToDatabase(CarData(i.toLong(), reg, model, modelYear, type, i.toString()/*vin*/, json))
            }
        }
    }

    override fun cancelJob() {
        jobTracker.cancel()
    }


}