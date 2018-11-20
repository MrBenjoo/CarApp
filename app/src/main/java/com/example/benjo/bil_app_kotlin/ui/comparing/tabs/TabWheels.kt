package com.example.benjo.bil_app_kotlin.ui.comparing.tabs

import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.CompareWheels
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_wheels.*

class TabWheels : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_wheels

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carOneData = getCarOneData((activity as HomeActivity).compare)
        val carTwoData = getCarTwoData((activity as HomeActivity).compare)
        val carModelOne = (activity as HomeActivity).resultCar1?.carInfo?.basic?.data?.model
        val carModelTwo = (activity as HomeActivity).resultCar2?.carInfo?.basic?.data?.model

        tv_tire_model_1.text = carModelOne
        tv_tire_back_1.text = carOneData.tireBack
        tv_tire_front_1.text = carOneData.tireFront

        tv_tire_model_2.text = carModelTwo
        tv_tire_back_2.text = carTwoData.tireBack
        tv_tire_front_2.text = carTwoData.tireFront


        tv_rim_model_1.text = carModelOne
        tv_rim_back_1.text = carOneData.rimBack
        tv_rim_front_1.text = carOneData.rimFront

        tv_rim_model_2.text = carModelTwo
        tv_rim_back_2.text = carTwoData.rimBack
        tv_rim_front_2.text = carTwoData.rimFront


    }

    private fun getCarTwoData(compare: Compare): CompareWheels = with(compare.carTwoData?.dimensions?.wheels!!) {
        CompareWheels(tireFront, tireBack, rimFront, rimBack)
    }

    private fun getCarOneData(compare: Compare): CompareWheels = with(compare.carOneData?.dimensions?.wheels!!) {
        CompareWheels(tireFront, tireBack, rimFront, rimBack)
    }
}