package com.example.benjo.bil_app_kotlin.ui.comparing.tabs

import com.example.benjo.bil_app_kotlin.ui.comparing.CompareRow
import com.example.benjo.bil_app_kotlin.ui.comparing.constants
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ComparData
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.CompareDimensionOther
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity

class TabDimensionOther: CompareRow() {

    override fun initList() {
        val carOneData = getCarOneData((activity as HomeActivity).compare)
        val carTwoData = getCarTwoData((activity as HomeActivity).compare)
        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_length),
                        carModelOne,
                        carOneData.length,
                        carModelTwo,
                        carTwoData.length
                )
        )
        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_width),
                        carModelOne,
                        carOneData.width,
                        carModelTwo,
                        carTwoData.width
                )
        )
        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_height),
                        carModelOne,
                        carOneData.height,
                        carModelTwo,
                        carTwoData.height
                )
        )
        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_axel_width),
                        carModelOne,
                        carOneData.axelWidth,
                        carModelTwo,
                        carTwoData.axelWidth
                )
        )
        renderRecyclerAdapter.setItems(list)
    }

    private fun getCarTwoData(compare: Compare?): CompareDimensionOther =
            with(compare?.carTwoData?.dimensions?.other!!) {
                CompareDimensionOther(length, width, height, axelWidth)
            }

    private fun getCarOneData(compare: Compare?): CompareDimensionOther =
            with(compare?.carOneData?.dimensions?.other!!) {
                CompareDimensionOther(length, width, height, axelWidth)
            }
}