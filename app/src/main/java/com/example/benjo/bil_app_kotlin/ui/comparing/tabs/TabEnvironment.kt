package com.example.benjo.bil_app_kotlin.ui.comparing.tabs

import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ItemModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererAdapter
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererCompareRow
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.CompareData
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.EnvModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.EnvRender
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.CompareEnvironment
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_COMMONN
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_ENVIRONMENT
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import kotlinx.android.synthetic.main.fragment_base.*


class TabEnvironment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_base

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = RendererAdapter()

        val list = arrayListOf<ItemModel>()

        val envRenderer = EnvRender()
        val commonRenderer = RendererCompareRow()

        commonRenderer.type = TYPE_COMMONN
        envRenderer.type = TYPE_ENVIRONMENT

        val carOneData = getCarOneData((activity as HomeActivity).compare)
        val carTwoData = getCarTwoData((activity as HomeActivity).compare)
        val carModelOne = (activity as HomeActivity).resultCar1?.carInfo?.basic?.data?.model
        val carModelTwo = (activity as HomeActivity).resultCar2?.carInfo?.basic?.data?.model

        with(ExplanationHandler()) {
            list.add(EnvModel(TYPE_ENVIRONMENT,
                    string(R.string.title_compare_fuel),

                    carModelOne,
                    fuelType(carOneData.fuel),
                    carModelTwo,
                    fuelType(carTwoData.fuel),

                    string(R.string.title_compare_eco_class),
                    carModelOne,
                    ecoClassType(carOneData.ecoClass),
                    carModelTwo,
                    ecoClassType(carTwoData.ecoClass),

                    string(R.string.title_compare_four_wheel_drive),
                    carModelOne,
                    boolType(carOneData.fourWheelDrive),
                    carModelTwo,
                    boolType(carTwoData.fourWheelDrive),

                    string(R.string.title_compare_transmission),
                    carModelOne,
                    transType(carOneData.transmission),
                    carModelTwo,
                    transType(carTwoData.transmission)
            ))

            list.add(CompareData(TYPE_COMMONN,
                    string(R.string.title_compare_consumption),
                    carModelOne,
                    carOneData.consumption,
                    carModelTwo,
                    carTwoData.consumption))

            list.add(CompareData(TYPE_COMMONN,
                    string(R.string.title_compare_co2),
                    carModelOne,
                    carOneData.co2,
                    carModelTwo,
                    carTwoData.co2))

            list.add(CompareData(TYPE_COMMONN,
                    string(R.string.title_compare_nox),
                    carModelOne,
                    carOneData.nox,
                    carModelTwo,
                    carTwoData.nox))

            list.add(CompareData(TYPE_COMMONN,
                    string(R.string.title_compare_thc_nox),
                    carModelOne,
                    carOneData.thcNox,
                    carModelTwo,
                    carTwoData.thcNox))
        }


        adapter.registerRenderer(envRenderer)
        adapter.registerRenderer(commonRenderer)
        adapter.setItems(list)
        recyclerview_base.adapter = adapter
    }

    private fun getCarOneData(compare: Compare): CompareEnvironment = with(compare.carOneData?.technical?.environment!!) {
        CompareEnvironment(fuel, consumption, co2, transmission, fourWheelDrive, nox, thcNox, ecoClass)
    }

    private fun getCarTwoData(compare: Compare): CompareEnvironment = with(compare.carTwoData?.technical?.environment!!) {
        CompareEnvironment(fuel, consumption, co2, transmission, fourWheelDrive, nox, thcNox, ecoClass)
    }

    private fun string(id: Int): String {
        return context.resources?.getString(id) ?: "N/A"
    }
}
