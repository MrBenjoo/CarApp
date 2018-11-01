package com.example.benjo.bil_app_kotlin.ui.comparing


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.benjo.bil_app_kotlin.data.model.ComparableData
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.adapters.CommonCompAdapter
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.recyclerview_comparing.*

class MotorFragment : BaseFragment() {

    private lateinit var CommonCompAdapter: CommonCompAdapter
    private lateinit var listOfMotorData: ArrayList<ComparableData>

    override fun layoutId(): Int = R.layout.recyclerview_comparing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rc_dim.setHasFixedSize(true)
        rc_dim.layoutManager = LinearLayoutManager(activity?.applicationContext)

        val compareResult = (activity as HomeActivity).compare
        setupHorsepower(compareResult)
        setupKilowatt(compareResult)
        setupCylinderVolume(compareResult)
        setupTopSpeed(compareResult)

        CommonCompAdapter = CommonCompAdapter(listOfMotorData)
        rc_dim.adapter = CommonCompAdapter
    }

    private fun setupTopSpeed(compare: Compare) {
        listOfMotorData.add(ComparableData(
                "Top Speed",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.motor?.top_speed,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.motor?.top_speed
        ))
    }

    private fun setupCylinderVolume(compare: Compare) {
        listOfMotorData.add(ComparableData(
                "Cylinder Volume",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.motor?.cylinder_volume,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.motor?.cylinder_volume
        ))
    }

    private fun setupKilowatt(compare: Compare) {
        listOfMotorData.add(ComparableData(
                "Kilowatt",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.motor?.power_kw,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.motor?.power_kw
        ))
    }

    private fun setupHorsepower(compare: Compare) {
        listOfMotorData = arrayListOf(ComparableData(
                "Horsepower",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.motor?.power_hp,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.motor?.power_hp
        ))
    }


}
