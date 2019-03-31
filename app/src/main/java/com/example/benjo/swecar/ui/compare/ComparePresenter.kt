package com.example.benjo.swecar.ui.compare

import com.example.benjo.swecar.ui.compare.data.DataProvider
import com.example.benjo.swecar.ui.compare.data.model.ItemCompareModel


class ComparePresenter(private val dataProvider: DataProvider) : CompareContract.Presenter {
    private val TAG = "ComparePresenter"
    private lateinit var view: CompareContract.View

    override fun attachView(view: CompareContract.View) {
        this.view = view
    }

    override fun onPageSelected(page: SelectedPageEvent<String>): ArrayList<ItemCompareModel> {
        return when (page) {
            is SelectedPageEvent.OnVehicleClick -> dataProvider.getVehicleData()
            is SelectedPageEvent.OnTechnicalClick -> onTechnicalClick(page.title)
            is SelectedPageEvent.OnDimensionsClick -> onDimensionClick(page.title)
        }
    }

    private fun onTechnicalClick(title: String): ArrayList<ItemCompareModel> = when (title) {
        view.titleEnvironment() -> dataProvider.getTechnicalData().getValue("environment")
        view.titleMotor() -> dataProvider.getTechnicalData().getValue("motor")
        view.titleOther() -> dataProvider.getTechnicalData().getValue("other")
        else -> arrayListOf()
    }

    private fun onDimensionClick(title: String): ArrayList<ItemCompareModel> = when (title) {
        view.titleWeights() -> dataProvider.getDimensionData().getValue("weights")
        view.titleWheels() -> dataProvider.getDimensionData().getValue("wheels")
        view.titleOther() -> dataProvider.getDimensionData().getValue("other")
        else -> arrayListOf()
    }

}