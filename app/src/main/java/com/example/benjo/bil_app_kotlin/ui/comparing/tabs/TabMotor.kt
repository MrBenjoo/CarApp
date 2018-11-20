package com.example.benjo.bil_app_kotlin.ui.comparing.tabs


import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.CompareMotor
import com.example.benjo.bil_app_kotlin.ui.comparing.CompareRow
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_COMMONN
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ComparData
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.R


class TabMotor : CompareRow() {


    override fun initList() {
        val carOneData = getCarOneData((activity as HomeActivity).compare)
        val carTwoData = getCarTwoData((activity as HomeActivity).compare)
        list.add(
                ComparData(
                        TYPE_COMMONN,
                        string(R.string.title_compare_horsepower),
                        carModelOne,
                        carOneData.horsepower,
                        carModelTwo,
                        carTwoData.horsepower
                )
        )
        list.add(
                ComparData(
                        TYPE_COMMONN,
                        string(R.string.title_compare_kilowatt),
                        carModelOne,
                        carOneData.kilowatt,
                        carModelTwo,
                        carTwoData.kilowatt
                )
        )
        list.add(
                ComparData(
                        TYPE_COMMONN,
                        string(R.string.title_compare_cylinder_volume),
                        carModelOne,
                        carOneData.volume,
                        carModelTwo,
                        carTwoData.volume
                )
        )
        list.add(
                ComparData(
                        TYPE_COMMONN,
                        string(R.string.title_compare_top_speed),
                        carModelOne,
                        carOneData.topSpeed,
                        carModelTwo,
                        carTwoData.topSpeed
                )
        )
        renderRecyclerAdapter.setItems(list)
    }

    private fun getCarTwoData(compare: Compare?): CompareMotor =
            with(compare?.carTwoData?.technical?.motor!!) {
                CompareMotor(horsepower, kilowatt, volume, topSpeed)
            }

    private fun getCarOneData(compare: Compare?): CompareMotor =
            with(compare?.carOneData?.technical?.motor!!) {
                CompareMotor(horsepower, kilowatt, volume, topSpeed)
            }



}
