package com.example.benjo.bil_app_kotlin.ui.comparing


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


class DimensionsFragment : BaseFragment() {
    private lateinit var CommonCompAdapter: CommonCompAdapter
    private lateinit var arrListDimensions: ArrayList<ComparableData>

    override fun layoutId(): Int = R.layout.recyclerview_comparing

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rc_dim.setHasFixedSize(true)
        rc_dim.layoutManager = LinearLayoutManager(activity?.applicationContext)

        val compareResult = (activity as HomeActivity).compare
        setupLength(compareResult)
        setupWidth(compareResult)
        setupHeight(compareResult)
        setupAxelWidth(compareResult)

        CommonCompAdapter = CommonCompAdapter(arrListDimensions)
        rc_dim.adapter = CommonCompAdapter
    }


    private fun setupLength(compare: Compare) {
        arrListDimensions = arrayListOf(ComparableData(
                "Length",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.dimensions!!.length,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.dimensions!!.length
        ))
    }

    private fun setupWidth(compare: Compare) {
        arrListDimensions.add(ComparableData(
                "Width",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.dimensions!!.width,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.dimensions!!.width
        ))
    }

    private fun setupHeight(compare: Compare) {
        arrListDimensions.add(ComparableData(
                "Height",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.dimensions!!.height,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.dimensions!!.height
        ))
    }

    private fun setupAxelWidth(compare: Compare) {
        arrListDimensions.add(ComparableData(
                "Axel Width",
                compare.car_1?.vehicle_data?.vehData!!.model,
                compare.car_1.technical_data?.dimensions!!.axel_width,
                compare.car_2?.vehicle_data?.vehData!!.model,
                compare.car_2.technical_data?.dimensions!!.axel_width
        ))
    }

}
