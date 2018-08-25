package com.example.benjo.bil_app_kotlin.saved

import android.content.Context
import android.util.Log
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.room.CarData
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.room.RoomGetCar
import com.example.benjo.bil_app_kotlin.room.RoomThread
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext

class SavedPresenter(val context: Context) : SavedContract.Presenter {
    private val TAG = "SavedPresenter"
    private var view: SavedContract.View? = null

    override fun getCarFromDB(vin: Int) {
        launch(UI) {
            val car = withContext(CommonPool) {
                CarDataBase.getInstance(context)?.carDataDao()?.getCar(vin)
            }
            if (car != null)
                showCar(car)
        }
    }

    override fun showCar(car: CarData) {
        view?.showCar(car)
    }


    override fun attachView(v: SavedContract.View) {
        view = v
    }

    override fun loadSavedCars() {
        launch(UI) {
            val list = withContext(CommonPool) {
                CarDataBase.getInstance(context)?.carDataDao()?.getAll()
            }
            if (list != null) {
                val arrayList = arrayListOf<CarData>()
                for (item in list) {
                    arrayList.add(CarData(null, item.vin, item.json))
                }
                showSavedCars(arrayList)
            }
        }
    }

    override fun showSavedCars(list: ArrayList<CarData>) {
        view?.updateView(list)
    }


}