package com.example.benjo.bil_app_kotlin.ui.comparing.tabs

import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.CompareVehicleData
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.ui.comparing.constants
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.FordonDataRenderer
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.FordonModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ItemModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererAdapter
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import kotlinx.android.synthetic.main.fragment_base.*

class TabFordonsData : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_base

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    fun initList() {
        val carModelOne = (activity as HomeActivity).resultCar1?.carInfo?.basic?.data?.model
        val carModelTwo = (activity as HomeActivity).resultCar2?.carInfo?.basic?.data?.model
        val carOneData = getCarOneData((activity as HomeActivity).compare)
        val carTwoData = getCarTwoData((activity as HomeActivity).compare)
        val renderer = FordonDataRenderer()
        val list = arrayListOf<ItemModel>()
        val renderRecyclerAdapter = RendererAdapter()
        renderer.type = constants.TYPE_FORDONS_DATA
        with(ExplanationHandler()) {
            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_model_year),
                            carModelOne,
                            carOneData.modelYear,
                            carModelTwo,
                            carTwoData.modelYear
                    )
            )
            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_vehicle_year),
                            carModelOne,
                            carOneData.vehicleYear,
                            carModelTwo,
                            carTwoData.vehicleYear
                    )
            )
            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_vehicle_type),
                            carModelOne,
                            carOneData.type,
                            carModelTwo,
                            carTwoData.type
                    )
            )
            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_reg),
                            carModelOne,
                            carOneData.reg,
                            carModelTwo,
                            carTwoData.reg
                    )
            )

            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_status),
                            carModelOne,
                            statusType(carOneData.status),
                            carModelTwo,
                            statusType(carTwoData.status)
                    )
            )

            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_color),
                            carModelOne,
                            carOneData.color,
                            carModelTwo,
                            carTwoData.color
                    )
            )

            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_chassi),
                            carModelOne,
                            carOneData.chassi,
                            carModelTwo,
                            carTwoData.chassi
                    )
            )

            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_category),
                            carModelOne,
                            carOneData.category,
                            carModelTwo,
                            carTwoData.category
                    )
            )
            list.add(
                    FordonModel(
                            constants.TYPE_FORDONS_DATA,
                            string(R.string.title_compare_eeg),
                            carModelOne,
                            carOneData.eeg,
                            carModelTwo,
                            carTwoData.eeg
                    )
            )
        }


        renderRecyclerAdapter.registerRenderer(renderer)
        renderRecyclerAdapter.setItems(list)
        recyclerview_base.adapter = renderRecyclerAdapter
    }

    private fun getCarTwoData(compare: Compare?): CompareVehicleData =
            with(compare?.carTwoData?.vehicle!!) {
                CompareVehicleData(modelYear, vehicleYear, type, reg,
                        vin, status, color, chassi, category, eeg)
            }

    private fun getCarOneData(compare: Compare?): CompareVehicleData =
            with(compare?.carOneData?.vehicle!!) {
                CompareVehicleData(modelYear, vehicleYear, type, reg,
                        vin, status, color, chassi, category, eeg)
            }

    fun string(id: Int): String? {
        return context?.resources?.getString(id) ?: "N/A"
    }
}
