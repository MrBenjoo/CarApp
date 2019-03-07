package com.example.benjo.bil_app_kotlin.ui.compare

import android.arch.lifecycle.ViewModel
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.Compare

class CompareViewModel : ViewModel() {
    private lateinit var compare : Compare

    fun setCompareData(compareData : Compare) {
        this.compare = compareData
    }

    fun getCompareData() : Compare = compare

    fun getCarOneModel() : String? = compare.carOneData?.carModel

    fun getCarTwoModel() : String? = compare.carTwoData?.carModel

}