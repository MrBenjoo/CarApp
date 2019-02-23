package com.example.benjo.bil_app_kotlin.ui.comparing.view


import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.ui.comparing.BaseCompareView
import com.example.benjo.bil_app_kotlin.utils.ExplanationHandler
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.environmentDataOne
import com.example.benjo.bil_app_kotlin.data.environmentDataTwo
import com.example.benjo.bil_app_kotlin.ui.comparing.model.CompareData
import com.example.benjo.bil_app_kotlin.ui.comparing.model.EnvModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.*
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_ENVIRONMENT
import kotlinx.android.synthetic.main.fragment_environment.view.*
import kotlinx.android.synthetic.main.view_compare_text_include.view.*


class EnvironmentView : BaseCompareView() {

    override fun initListItems() {
        val carOneData = compare.environmentDataOne()
        val carTwoData = compare.environmentDataTwo()
        val rendererEnvironment = RendererEnvironmentView().also { it.type = RENDERER_TYPE_ENVIRONMENT }
        val rendererCommon = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }

        with(ExplanationHandler()) {
            listOfItems.add(
                    EnvModel(RENDERER_TYPE_ENVIRONMENT,
                            string(R.string.title_compare_fuel),
                            carOneModel,
                            fuelType(carOneData?.fuel),
                            carTwoModel,
                            fuelType(carTwoData?.fuel),

                            string(R.string.title_compare_eco_class),
                            carOneModel,
                            ecoClassType(carOneData?.ecoClass),
                            carTwoModel,
                            ecoClassType(carTwoData?.ecoClass),

                            string(R.string.title_compare_four_wheel_drive),
                            carOneModel,
                            boolType(carOneData?.fourWheelDrive),
                            carTwoModel,
                            boolType(carTwoData?.fourWheelDrive),

                            string(R.string.title_compare_transmission),
                            carOneModel,
                            transType(carOneData?.transmission),
                            carTwoModel,
                            transType(carTwoData?.transmission)
                    ))

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_consumption),
                            carOneModel,
                            carOneData?.consumption,
                            carTwoModel,
                            carTwoData?.consumption)
            )

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_co2),
                            carOneModel,
                            carOneData?.co2,
                            carTwoModel,
                            carTwoData?.co2)
            )

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_nox),
                            carOneModel,
                            carOneData?.nox,
                            carTwoModel,
                            carTwoData?.nox)
            )

            listOfItems.add(
                    CompareData(RENDERER_TYPE_COMMON,
                            string(R.string.title_compare_thc_nox),
                            carOneModel,
                            carOneData?.thcNox,
                            carTwoModel,
                            carTwoData?.thcNox)
            )
        }
        adapterRenderer.registerRenderer(rendererEnvironment)
        adapterRenderer.registerRenderer(rendererCommon)
    }

}


class RendererEnvironmentView : Renderer<EnvModel, EnvironmentViewHolder>() {

    override fun createViewHolder(parent: ViewGroup?): EnvironmentViewHolder {
        return EnvironmentViewHolder(
                LayoutInflater.from(parent?.context).inflate(
                        R.layout.fragment_environment,
                        parent,
                        false
                )
        )
    }

    override fun bindView(model: EnvModel, holder: EnvironmentViewHolder) {
        with(holder) {
            fuelTitle.text = model.fuelTitle
            fuelCarOne.text = model.fuelCarOne ?: "N/A"
            fuelCarOneVal.text = model.fuelCarOneVal ?: "N/A"
            fuelCarTwo.text = model.fuelCarTwo ?: "N/A"
            fuelCarTwoVal.text = model.fuelCarTwoVal ?: "N/A"

            ecoTitle.text = model.ecoTitle
            ecoCarOne.text = model.ecoCarOne ?: "N/A"
            ecoCarOneVal.text = model.ecoCarOneVal ?: "N/A"
            ecoCarTwo.text = model.ecoCarTwo ?: "N/A"
            ecoCarTwoVal.text = model.ecoCarTwoVal ?: "N/A"

            fourTitle.text = model.fourTitle
            fourCarOne.text = model.fourCarOne ?: "N/A"
            fourCarOneVal.text = model.fourCarOneVal ?: "N/A"
            fourCarTwo.text = model.fourCarTwo ?: "N/A"
            fourCarTwoVal.text = model.fourCarTwoVal ?: "N/A"

            transmissionTitle.text = model.transmissionTitle
            transmissionCarOne.text = model.transmissionCarOne ?: "N/A"
            transmissionCarOneVal.text = model.transmissionCarOneVal ?: "N/A"
            transmissionCarTwo.text = model.transmissionCarTwo ?: "N/A"
            transmissionCarTwoVal.text = model.transmissionCarTwoVal ?: "N/A"
        }
    }
}

class EnvironmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val fuelTitle = itemView.fuel.tv_compare_title
    val fuelCarOne = itemView.fuel.tv_compare_car_one
    val fuelCarOneVal = itemView.fuel.tv_compare_car_one_val
    val fuelCarTwo = itemView.fuel.tv_compare_car_two
    val fuelCarTwoVal = itemView.fuel.tv_compare_car_two_val

    val ecoTitle = itemView.ecoclass.tv_compare_title
    val ecoCarOne = itemView.ecoclass.tv_compare_car_one
    val ecoCarOneVal = itemView.ecoclass.tv_compare_car_one_val
    val ecoCarTwo = itemView.ecoclass.tv_compare_car_two
    val ecoCarTwoVal = itemView.ecoclass.tv_compare_car_two_val

    val fourTitle = itemView.fourwheeldrive.tv_compare_title
    val fourCarOne = itemView.fourwheeldrive.tv_compare_car_one
    val fourCarOneVal = itemView.fourwheeldrive.tv_compare_car_one_val
    val fourCarTwo = itemView.fourwheeldrive.tv_compare_car_two
    val fourCarTwoVal = itemView.fourwheeldrive.tv_compare_car_two_val

    val transmissionTitle = itemView.transmission.tv_compare_title
    val transmissionCarOne = itemView.transmission.tv_compare_car_one
    val transmissionCarOneVal = itemView.transmission.tv_compare_car_one_val
    val transmissionCarTwo = itemView.transmission.tv_compare_car_two
    val transmissionCarTwoVal = itemView.transmission.tv_compare_car_two_val
}
