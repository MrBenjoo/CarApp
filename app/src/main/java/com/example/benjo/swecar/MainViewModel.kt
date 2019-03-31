package com.example.benjo.swecar

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.example.benjo.swecar.ui.compare.data.model.Compare

class MainViewModel : ViewModel() {
    private lateinit var compare : Compare
    private lateinit var searchResponse: SearchResponse

    fun setCompareData(compareData : Compare) {
        this.compare = compareData
    }

    fun getCompareData() : Compare = compare

    fun getCarOneModel() : String? = compare.carOneData?.carModel

    fun getCarTwoModel() : String? = compare.carTwoData?.carModel

    fun setSearchResponse(response: SearchResponse) {
        Log.d("MainViewModel", "OnSetNewSearchResponse: " + response.carInfo.toString())
        this.searchResponse = response
    }

    fun getSearchResponse() : SearchResponse = searchResponse

}