package com.example.benjo.bil_app_kotlin.saved

import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.room.CarData

interface SavedContract {

    interface View : BaseView<Presenter> {
        fun updateView(list: ArrayList<CarData>)
        fun showCar(car: CarData)
    }

    interface Presenter : BasePresenter {
        fun loadSavedCars()
        fun attachView(view : View)
        fun showSavedCars(list: ArrayList<CarData>)
        fun getCarFromDB(vin: Int)
        fun showCar(car: CarData)
    }

    interface BasePresenter

}