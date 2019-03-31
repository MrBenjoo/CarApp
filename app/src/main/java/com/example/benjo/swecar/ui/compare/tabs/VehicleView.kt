package com.example.benjo.swecar.ui.compare.tabs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.swecar.R
import com.example.benjo.swecar.ui.compare.CompareBaseView
import com.example.benjo.swecar.ui.compare.CompareContract
import com.example.benjo.swecar.ui.compare.SelectedPageEvent
import com.example.benjo.swecar.ui.compare.data.model.VehicleCompareModel
import com.example.benjo.swecar.ui.compare.renderer.Renderer
import com.example.benjo.swecar.utils.Constants.Companion.RENDERER_TYPE_VEHICLE
import kotlinx.android.synthetic.main.view_compare_row_text_include.view.*

class VehicleView : CompareBaseView(), CompareContract.View {

    override fun initListItems() {
        val renderer = RendererVehicleView().also { it.type = RENDERER_TYPE_VEHICLE }
        presenter.attachView(this)
        listOfItems = presenter.onPageSelected(SelectedPageEvent.OnVehicleClick)
        adapterRenderer.registerRenderer(renderer)
    }

}

class RendererVehicleView : Renderer<VehicleCompareModel, VehicleViewHolder>() {

    override fun createViewHolder(parent: ViewGroup?): VehicleViewHolder {
        return VehicleViewHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.view_compare_row_text_include,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: VehicleCompareModel, holder: VehicleViewHolder) {
        with(holder) {
            title.text = model.title
            carModelOne.text = model.carOneModel
            carOneData.text = model.carOneData
            carModelTwo.text = model.carTwoModel
            carTwoData.text = model.carTwoData
        }
    }
}


class VehicleViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    val title = itemView.tv_compare_row_text_title
    val carModelOne = itemView.tv_compare_row_text_model_one
    val carOneData = itemView.tv_compare_row_text_model_one_val
    val carModelTwo = itemView.tv_compare_row_text_model_two
    val carTwoData = itemView.tv_compare_row_text_model_two_val
}
