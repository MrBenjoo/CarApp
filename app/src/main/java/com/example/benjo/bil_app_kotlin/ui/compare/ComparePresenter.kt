package com.example.benjo.bil_app_kotlin.ui.compare

import com.example.benjo.bil_app_kotlin.ui.compare.data.DataProvider
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.ItemModel


class ComparePresenter(private val dataProvider: DataProvider) : CompareContract.Presenter {
    private val TAG = "ComparePresenter"
    private lateinit var view: CompareContract.View

    override fun attachView(view: CompareContract.View) {
        this.view = view
    }

    override fun onPageSelected(page: SelectedPageEvent<String>): ArrayList<ItemModel> {
        return when (page) {
            is SelectedPageEvent.OnVehicleClick -> dataProvider.getVehicleData()
            is SelectedPageEvent.OnTechnicalClick -> onTechnicalClick(page.title)
            is SelectedPageEvent.OnDimensionsClick -> onDimensionClick(page.title)
        }
    }

    private fun onTechnicalClick(title: String): ArrayList<ItemModel> = when (title) {
        view.titleEnvironment() -> dataProvider.getTechnicalData().getValue("environment")
        view.titleMotor() -> dataProvider.getTechnicalData().getValue("motor")
        view.titleOther() -> dataProvider.getTechnicalData().getValue("other")
        else -> arrayListOf()
    }

    private fun onDimensionClick(title: String): ArrayList<ItemModel> = when (title) {
        view.titleWeights() -> dataProvider.getDimensionData().getValue("weights")
        view.titleWheels() -> dataProvider.getDimensionData().getValue("wheels")
        view.titleOther() -> dataProvider.getDimensionData().getValue("other")
        else -> arrayListOf()
    }

}