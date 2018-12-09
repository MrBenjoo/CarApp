package com.example.benjo.bil_app_kotlin.ui.comparing.view

import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.ui.comparing.Compare
import kotlinx.android.synthetic.main.fragment_wheels.*

class WheelsView : BaseFragment() {
    private lateinit var compare: Compare

    override fun layoutId(): Int = R.layout.fragment_wheels


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        compare = (activity as MainActivity).compare
        initView()
    }

    private fun initView() {
        val carOneData = compare.wheelsDataOne()
        val carTwoData = compare.wheelsDataTwo()
        val carOneModel = compare.carOneData?.carModel
        val carTwoModel = compare.carTwoData?.carModel

        tv_tire_model_1.text = carOneModel
        tv_tire_back_1.text = carOneData?.tireBack
        tv_tire_front_1.text = carOneData?.tireFront

        tv_tire_model_2.text = carTwoModel
        tv_tire_back_2.text = carTwoData?.tireBack
        tv_tire_front_2.text = carTwoData?.tireFront

        tv_rim_model_1.text = carOneModel
        tv_rim_back_1.text = carOneData?.rimBack
        tv_rim_front_1.text = carOneData?.rimFront

        tv_rim_model_2.text = carTwoModel
        tv_rim_back_2.text = carTwoData?.rimBack
        tv_rim_front_2.text = carTwoData?.rimFront
    }

}