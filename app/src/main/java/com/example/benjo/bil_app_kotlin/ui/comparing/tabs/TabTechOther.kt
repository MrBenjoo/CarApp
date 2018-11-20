package com.example.benjo.bil_app_kotlin.ui.comparing.tabs

import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.CompareTechnicalOther
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_COMMONN
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_TECH_OTHER
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.*
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import kotlinx.android.synthetic.main.fragment_base.*


class TabTechOther : BaseFragment() {
    lateinit var renderRecyclerAdapter: RendererAdapter

    override fun layoutId(): Int = R.layout.fragment_base

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderRecyclerAdapter = RendererAdapter()

        val compareRowRenderer = RendererCompareRow()
        compareRowRenderer.type = TYPE_COMMONN

        val techOtherRenderer = TechOtherRenderer()
        techOtherRenderer.type = TYPE_TECH_OTHER

        val carOneData = getCarOneData((activity as HomeActivity).compare)
        val carTwoData = getCarTwoData((activity as HomeActivity).compare)
        val carModelOne = (activity as HomeActivity).resultCar1?.carInfo?.basic?.data?.model
        val carModelTwo = (activity as HomeActivity).resultCar2?.carInfo?.basic?.data?.model


        val list = arrayListOf<ItemModel>()
        with(ExplanationHandler()) {
            list.add(TechOtherModel(TYPE_TECH_OTHER,
                    string(R.string.title_compare_number_of_passengers),
                    carModelOne,
                    carOneData.passengers,
                    carModelTwo,
                    carTwoData.passengers,

                    string(R.string.title_compare_passenger_airbag),
                    carModelOne,
                    boolType(carOneData.passengerAirbag),
                    carModelTwo,
                    boolType(carTwoData.passengerAirbag),

                    string(R.string.title_compare_hitch),
                    carModelOne,
                    carOneData.hitch,
                    carModelTwo,
                    carTwoData.hitch))
        }


        list.add(CompareData(TYPE_COMMONN,
                string(R.string.title_compare_sound_level),
                carModelOne,
                carOneData.soundLevel,
                carModelTwo,
                carTwoData.soundLevel))


        renderRecyclerAdapter.registerRenderer(compareRowRenderer)
        renderRecyclerAdapter.registerRenderer(techOtherRenderer)
        renderRecyclerAdapter.setItems(list)

        recyclerview_base.adapter = renderRecyclerAdapter
    }

    private fun getCarOneData(compare: Compare): CompareTechnicalOther = with(compare.carOneData?.technical?.other!!) {
        CompareTechnicalOther(soundLevel, passengers, passengerAirbag, hitch)
    }

    private fun getCarTwoData(compare: Compare): CompareTechnicalOther = with(compare.carTwoData?.technical?.other!!) {
        CompareTechnicalOther(soundLevel, passengers, passengerAirbag, hitch)
    }

    private fun string(id: Int): String {
        return context.resources?.getString(id) ?: "N/A"
    }

    
}