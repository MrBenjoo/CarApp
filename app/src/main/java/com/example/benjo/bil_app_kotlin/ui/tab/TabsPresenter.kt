package com.example.benjo.bil_app_kotlin.ui.tab

import android.util.Log
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import kotlinx.coroutines.android.Main
import retrofit2.Response
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class TabsPresenter(val view: TabsContract.ViewTabs,
                    val carRepository: CarRepository) : TabsContract.TabsPresenter, CoroutineScope {
    private val TAG = "TabsPresenter"

    private var jobTracker: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun search(reg: String?): Result? {
        var mResponse: Result? = null
        val connected = CommonUtils().isConnected(view.getContext())
        if (connected) {
            // the real code that will be used
            /*SearchRegProvider
                    .provideSearchReg()
                    .searchReg(reg)
                    .enqueue(Lambda().callback { throwable, response ->
                        throwable.let { /*onFailure(throwable)*/ }
                        response.let {
                            mResponse = response
                        }
                    })*/

            // Simulate a fake json response
            val randomInt = Random.nextInt(2) + 1 // between 0 (inclusive) and n (exclusive)
            val jsonFileName = "bil_" + randomInt.toString() + ".json"
            Log.d(TAG, jsonFileName)
            return GsonBuilder().create().fromJson(
                    CommonUtils()
                            .loadJSONFromAsset(view.getContext(),
                                    jsonFileName), Result::class.java)
        } else return mResponse
    }

    override fun validateResponse(response: Response<Result>?): Result? {
        when (response?.isSuccessful) {
            true -> return response.body()
            else -> return null
        }
    }

    private fun saveToDatabase(car: CarData): Boolean {
        var saved = false
        launch {
            val carFromRoom = async(Dispatchers.IO) { carRepository.getCar(car.vin) }.await()
            if (carFromRoom == null) {
                launch(Dispatchers.IO) { carRepository.insertCar(car) }
                saved = true
            }
        }
        return saved
    }

    override fun onImgSaveClicked(result: Result?) {
        with(result?.carInfo!!) {
            val reg = attributes?.regno!!
            val model = basic?.data?.model!!
            val modelYear = basic.data.model_year!!
            val type = basic.data.type!!
            val vin = attributes.vin!!
            val json = GsonBuilder().create().toJson(result)
            for (i in 0..10) {
                val saved = saveToDatabase(CarData(i.toLong(), reg, model, modelYear, type, i.toString()/*vin*/, json))
                if (saved) view.showText(R.string.view_tabs_car_saved)
                else view.showText(R.string.view_tabs_car_not_saved)
            }
        }
    }


}