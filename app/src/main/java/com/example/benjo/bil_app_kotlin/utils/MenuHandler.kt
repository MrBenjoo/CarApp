package com.example.benjo.bil_app_kotlin.utils

import android.graphics.Color
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.MenuFragment
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

class MenuHandler(val view: View, val listener: OnChartValueSelectedListener) {
    private lateinit var dataSet: PieDataSet
    private lateinit var pieData: PieData

    init {
        initDataSet()
        initPieData()
        initPieChart()
    }

    private fun initDataSet() {
        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(MenuFragment.TEKNISK, "Tekniskdata"))
        entries.add(PieEntry(MenuFragment.DIMENSIONER, "Fordonsdata"))
        entries.add(PieEntry(MenuFragment.ÖVRIGT, "Övrigt"))

        dataSet = PieDataSet(entries, "")
        with(dataSet) {
            sliceSpace = 3f
            selectionShift = 5f
            colors = arrayListOf(Color.parseColor("#4FE3C1"))
            valueTextSize = 16f
        }
    }

    private fun initPieData() {
        pieData = PieData(dataSet)
        pieData.setValueTextSize(0f)
    }

    private fun initPieChart() {
        with(view.findViewById<PieChart>(R.id.pie_comp_menu)) {
            data = pieData
            description.isEnabled = false
            legend?.isEnabled = false
            setHoleColor(Color.TRANSPARENT)
            setOnChartValueSelectedListener(listener)
            animateY(1000, Easing.EasingOption.EaseInQuad)
        }
    }


}
