package com.example.benjo.bil_app_kotlin.ui.comparing.tabs


import com.example.benjo.bil_app_kotlin.ui.comparing.CompareRow
import com.example.benjo.bil_app_kotlin.ui.comparing.constants
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ComparData
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.CompareWeights
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity


class TabWeights: CompareRow() {


    override fun initList() {
        val carOneData = getCarOneData((activity as HomeActivity).compare)
        val carTwoData = getCarTwoData((activity as HomeActivity).compare)
        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_total_weight),
                        carModelOne,
                        carOneData.totalWeight,
                        carModelTwo,
                        carTwoData.totalWeight)
        )
        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_load_weight),
                        carModelOne,
                        carOneData.loadWeight,
                        carModelTwo,
                        carTwoData.loadWeight)
        )

        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_trailer_weight),
                        carModelOne,
                        carOneData.trailerWeight,
                        carModelTwo,
                        carTwoData.trailerWeight)
        )

        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_unbraked_trailer_weight),
                        carModelOne,
                        carOneData.unbrakedTrailerWeight,
                        carModelTwo,
                        carTwoData.unbrakedTrailerWeight)
        )

        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_trailer_weight_b),
                        carModelOne,
                        carOneData.trailerWeightB,
                        carModelTwo,
                        carTwoData.trailerWeightB)
        )

        list.add(
                ComparData(
                        constants.TYPE_COMMONN,
                        string(R.string.title_compare_trailer_weight_be),
                        carModelOne,
                        carOneData.trailerWeightBe,
                        carModelTwo,
                        carTwoData.trailerWeightBe)
        )
        
        
        renderRecyclerAdapter.setItems(list)
    }

    private fun getCarTwoData(compare: Compare): CompareWeights = with(compare.carTwoData?.dimensions?.weights!!) {
        CompareWeights(kerbWeight, totalWeight, loadWeight, trailerWeight,
                trailerWeightB, trailerWeightBe, unbrakedTrailerWeight, carriageWeight)
    }

    private fun getCarOneData(compare: Compare): CompareWeights = with(compare.carOneData?.dimensions?.weights!!) {
        CompareWeights(kerbWeight, totalWeight, loadWeight, trailerWeight,
                trailerWeightB, trailerWeightBe, unbrakedTrailerWeight, carriageWeight)
    }


}