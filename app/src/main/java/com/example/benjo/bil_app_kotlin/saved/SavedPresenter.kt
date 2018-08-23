package com.example.benjo.bil_app_kotlin.saved

import android.content.Context
import android.util.Log
import com.example.benjo.bil_app_kotlin.room.CarData
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.room.RoomGetCar
import com.example.benjo.bil_app_kotlin.room.RoomThread

class SavedPresenter(val context : Context): SavedContract.Presenter {
    private val TAG = "SavedPresenter"
    private var view: SavedContract.View? = null

    override fun getCarFromDB(vin: Int) {
        Thread(
                RoomGetCar(
                        CarDataBase.getInstance(context),
                        this,
                        vin))
                .start()
    }

    override fun showCar(car: CarData) {
        view?.showCar(car)
    }

    override fun attachView(view : SavedContract.View) {
        this.view = view
    }

    override fun loadSavedCars() {
        Log.d(TAG, "*************** loadSavedCars ***************")
        Thread(
                RoomThread(
                        CarDataBase.getInstance(context),
                        this))
                .start()
    }

    override fun showSavedCars(list: ArrayList<CarData>) {
        view?.updateView(list)
    }


}