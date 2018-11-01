package com.example.benjo.bil_app_kotlin.ui.comparing


import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_wheels.*


class WheelsFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_wheels

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val compareResult = (activity as HomeActivity).compare
        setCar1Wheels(compareResult)
        setCar2Wheels(compareResult)
    }

    private fun setCar1Wheels(compareResult: Compare?) = with(compareResult?.car_1!!) {
        tv_tire_model_1.text = vehicle_data?.vehData?.model
        tv_tire_front_1.text = technical_data?.wheels?.tire_front
        tv_tire_back_1.text = technical_data?.wheels?.tire_back
        tv_rim_model_1.text = vehicle_data?.vehData?.model
        tv_rim_front_1.text = technical_data?.wheels?.rim_front
        tv_rim_back_1.text = technical_data?.wheels?.rim_back
    }

    private fun setCar2Wheels(compareResult: Compare?) = with(compareResult?.car_2!!) {
        tv_tire_model_2.text = vehicle_data?.vehData?.model
        tv_tire_front_2.text = technical_data?.wheels?.tire_front
        tv_tire_back_2.text = technical_data?.wheels?.tire_back
        tv_rim_model_2.text = vehicle_data?.vehData?.model
        tv_rim_front_2.text = technical_data?.wheels?.rim_front
        tv_rim_back_2.text = technical_data?.wheels?.rim_back
    }


}
