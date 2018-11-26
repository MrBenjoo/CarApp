package com.example.benjo.bil_app_kotlin.ui.saved

import android.arch.lifecycle.ViewModel
import com.example.benjo.bil_app_kotlin.data.room.CarData

class SavedViewModel : ViewModel() {
    var carList: ArrayList<CarData> = arrayListOf()
}