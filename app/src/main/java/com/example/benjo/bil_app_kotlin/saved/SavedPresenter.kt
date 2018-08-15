package com.example.benjo.bil_app_kotlin.saved

import android.content.Context
import android.util.Log
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.sections.Row

class SavedPresenter(val context : Context): SavedContract.Presenter {
    private val TAG = "SavedPresenter"
    private var view: SavedContract.View? = null


    override fun attachView(view : SavedContract.View) {
        this.view = view
    }

    override fun loadSavedCars() {
        Log.d(TAG, "*************** loadSavedCars ***************")
        Thread(RoomThread(CarDataBase.getInstance(context), this)).start()
    }

    override fun showSavedCars(list: ArrayList<Row>) {
        view?.updateView(list)
    }


}