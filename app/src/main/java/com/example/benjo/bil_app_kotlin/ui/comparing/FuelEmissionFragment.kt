package com.example.comparing.ui.comparing


import android.content.res.Resources
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.FuelEmissionAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.ComparableData
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.FuelEcoEmission
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.recyclerview_comparing.*

class FuelEmissionFragment : BaseFragment() {
    private lateinit var fuelEmissionAdapter: FuelEmissionAdapter
    private lateinit var arrListFuelEmission: ArrayList<Any>

    override fun layoutId(): Int = R.layout.recyclerview_comparing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rc_dim.setHasFixedSize(true)
        rc_dim.layoutManager = LinearLayoutManager(activity?.applicationContext)

        val compareResult = (activity as HomeActivity).compare
        setupFuelEcoEmission(compareResult)
        setupConsumption(compareResult)
        setupCo2(compareResult)
        setupNox(compareResult)
        setupThcNox(compareResult)


        fuelEmissionAdapter = FuelEmissionAdapter(arrListFuelEmission)
        rc_dim.adapter = fuelEmissionAdapter
    }

    private fun setupThcNox(compare: Compare) {
        arrListFuelEmission.add(ComparableData(
                context.resources.getString(R.string.title_compare_thc_nox),
                compare.car_1?.vehicle_data?.vehData?.model,
                compare.car_1?.technical_data?.fuelEmission?.thc_nox,
                compare.car_2?.vehicle_data?.vehData?.model,
                compare.car_2?.technical_data?.fuelEmission?.thc_nox
        ))
    }

    private fun setupNox(compare: Compare) {
        arrListFuelEmission.add(ComparableData(
                context.resources.getString(R.string.title_compare_nox),
                compare.car_1?.vehicle_data?.vehData?.model,
                compare.car_1?.technical_data?.fuelEmission?.nox,
                compare.car_2?.vehicle_data?.vehData?.model,
                compare.car_2?.technical_data?.fuelEmission?.nox
        ))
    }

    private fun setupCo2(compare: Compare) {
        arrListFuelEmission.add(ComparableData(
                context.resources.getString(R.string.title_compare_co2),
                compare.car_1?.vehicle_data?.vehData?.model,
                compare.car_1?.technical_data?.fuelEmission?.co2,
                compare.car_2?.vehicle_data?.vehData?.model,
                compare.car_2?.technical_data?.fuelEmission?.co2
        ))
    }

    private fun setupConsumption(compare: Compare) {
        arrListFuelEmission.add(ComparableData(
                context.resources.getString(R.string.title_compare_consumption),
                compare.car_1?.vehicle_data?.vehData?.model,
                compare.car_1?.technical_data?.fuelEmission?.consumption,
                compare.car_2?.vehicle_data?.vehData?.model,
                compare.car_2?.technical_data?.fuelEmission?.consumption
        ))
    }

    private fun setupFuelEcoEmission(compare: Compare) {
        arrListFuelEmission = arrayListOf(FuelEcoEmission(
                context.resources.getString(R.string.title_compare_fuel),
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.fuelEmission?.fuel,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.fuelEmission?.fuel,
                context.resources.getString(R.string.title_compare_eco_class),
                compare.car_1.vehicle_data.vehData.model,
                compare.car_1.technical_data?.fuelEmission?.eco_class,
                compare.car_2.vehicle_data.vehData.model,
                compare.car_2.technical_data?.fuelEmission?.eco_class,
                context.resources.getString(R.string.title_compare_emission),
                compare.car_1.vehicle_data.vehData.model,
                compare.car_1.technical_data?.fuelEmission?.emission,
                compare.car_2.vehicle_data.vehData.model,
                compare.car_2.technical_data?.fuelEmission?.emission
        ))
    }


}
