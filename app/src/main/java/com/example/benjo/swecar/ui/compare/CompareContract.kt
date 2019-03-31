package com.example.benjo.swecar.ui.compare

import com.example.benjo.swecar.base.BasePresenter
import com.example.benjo.swecar.base.BaseView
import com.example.benjo.swecar.ui.compare.data.model.ItemCompareModel

interface CompareContract {

    interface View : BaseView<Presenter> {
        fun titleWeights() : String
        fun titleOther() : String
        fun titleWheels() : String
        fun titleEnvironment() : String
        fun titleMotor() : String
    }

    interface Presenter : BasePresenter<View> {
        fun onPageSelected(page: SelectedPageEvent<String>) : ArrayList<ItemCompareModel>
    }

}


sealed class SelectedPageEvent<out T> {
    object OnVehicleClick : SelectedPageEvent<Nothing>()
    data class OnTechnicalClick<out String>(val title: String) : SelectedPageEvent<String>()
    data class OnDimensionsClick<out String>(val title: String) : SelectedPageEvent<String>()
}