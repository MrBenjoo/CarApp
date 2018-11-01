package com.example.benjo.bil_app_kotlin.ui.tab

import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import retrofit2.Response

class TabsPresenter(val view: TabsContract.ViewTabs, val carRepository: CarRepository) : TabsContract.TabsPresenter {
    private val TAG = "TabsPresenter"

    override fun search(reg: String?): Response<Result>? {
        var mResponse: Response<Result>? = null
        /*val connected = CommonUtils().isConnected(view.getContext())
        if (connected) {
            SearchRegProvider
                    .provideSearchReg()
                    .searchReg(reg)
                    .enqueue(Lambda().callback { throwable, response ->
                        throwable.let { /*onFailure(throwable)*/ }
                        response.let {
                            mResponse = response
                        }
                    })
        }

        return mResponse*/

        return mResponse
    }

    override fun validateResponse(response: Response<Result>?): Result? =
            when (response?.isSuccessful) {
                true -> response.body()
                else -> null
            }

    override fun saveToDatabase(car : CarData): Boolean =
            when (carRepository.getCar(car.vin) == null) {
                true -> {
                    carRepository.insertCar(car)
                    true
                }
                else -> false
            }

}