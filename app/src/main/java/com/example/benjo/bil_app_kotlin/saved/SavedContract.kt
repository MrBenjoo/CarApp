package com.example.benjo.bil_app_kotlin.saved

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.room.CarData

interface SavedContract {

    interface View : BaseView<Presenter> {
        fun updateView(list: ArrayList<CarData>)
        fun showCar(car: CarData)
        fun showDialogOnMultipleDeletion()
        fun onDestroyActionMode()
    }

    interface Presenter : BasePresenter<View> {
        fun loadSavedCars()
        fun getCarFromDB(vin: String)
        fun deleteCarFromDB(vin: String): Boolean
        fun showSavedCars(list: ArrayList<CarData>)
        fun showCar(car: CarData)
        fun onMultipleClick(multiList: ArrayList<CarData>,
                            singleList: ArrayList<CarData>,
                            position: Int): ArrayList<CarData>

    }


}