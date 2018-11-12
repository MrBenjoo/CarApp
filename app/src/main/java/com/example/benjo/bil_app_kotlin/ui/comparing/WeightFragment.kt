package com.example.benjo.bil_app_kotlin.ui.comparing


import android.content.res.Resources
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.CommonCompAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.ComparableData
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.recyclerview_comparing.*


class WeightFragment : BaseFragment() {
    private lateinit var CommonCompAdapter: CommonCompAdapter
    private lateinit var weights: ArrayList<ComparableData>

    override fun layoutId(): Int = R.layout.recyclerview_comparing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rc_dim.setHasFixedSize(true)
        rc_dim.layoutManager = LinearLayoutManager(activity?.applicationContext)

        val compareResult = (activity as HomeActivity).compare
        setupKerbWeight(compareResult)
        setupTotalWeight(compareResult)
        setupLoadWeight(compareResult)
        setupTrailerWeight(compareResult)
        setupUnbrakedTrailerWeight(compareResult)
        setupTrailerWeightB(compareResult)
        setupTrailerWeightBE(compareResult)
        setupCarriageWeight(compareResult)

        CommonCompAdapter = CommonCompAdapter(weights)
        rc_dim.adapter = CommonCompAdapter
    }

    private fun setupCarriageWeight(compare: Compare) {
        weights.add(ComparableData(
                context.resources.getString(R.string.title_compare_carriage_weight),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.carriage_weight,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.carriage_weight))
    }

    private fun setupTrailerWeightBE(compare: Compare) {
        weights.add(ComparableData(
                context.resources.getString(R.string.title_compare_trailer_weight_be),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.trailer_weight_be,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.trailer_weight_be))

    }

    private fun setupTrailerWeightB(compare: Compare) {
        weights.add(ComparableData(
                context.resources.getString(R.string.title_compare_trailer_weight_b),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.trailer_weight_b,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.trailer_weight_b))
    }

    private fun setupUnbrakedTrailerWeight(compare: Compare) {
        weights.add(ComparableData(
                context.resources.getString(R.string.title_compare_unbraked_trailer_weight),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.unbraked_trailer_weight,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.unbraked_trailer_weight))
    }

    private fun setupTrailerWeight(compare: Compare) {
        weights.add(ComparableData(
                context.resources.getString(R.string.title_compare_trailer_weight),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.trailer_weight,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.trailer_weight))
    }

    private fun setupLoadWeight(compare: Compare) {
        weights.add(ComparableData(
                context.resources.getString(R.string.title_compare_load_weight),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.total_weight,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.total_weight))
    }

    private fun setupTotalWeight(compare: Compare) {
        weights.add(ComparableData(
                context.resources.getString(R.string.title_compare_total_weight),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.total_weight,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.total_weight))
    }

    private fun setupKerbWeight(compare: Compare) {
        weights = arrayListOf(ComparableData(
                context.resources.getString(R.string.title_compare_kerb_weight),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.weights?.kerb_weight,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.weights?.kerb_weight
        ))
    }


}
