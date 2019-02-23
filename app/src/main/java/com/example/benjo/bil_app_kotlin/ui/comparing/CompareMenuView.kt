package com.example.benjo.bil_app_kotlin.ui.comparing


import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.utils.mainActivity
import com.example.benjo.bil_app_kotlin.utils.navigate
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import kotlinx.android.synthetic.main.fragment_menu.*


class CompareMenuView : BaseFragment(), OnChartValueSelectedListener {
    private val TAG = "CompareMenuView"
    private lateinit var dataSet: PieDataSet
    private lateinit var pieData: PieData

    companion object {
        val DIMENSIONER = 1.0001F
        val TEKNISK = 1.0002F
        val FORDONSDATA = 1.0003F
    }

    override fun layoutId(): Int = R.layout.fragment_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataSet()
        initPieData()
        initPieChart()
    }

    private fun initDataSet() {
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(TEKNISK, getString(R.string.compare_menu_technical)))
        entries.add(PieEntry(DIMENSIONER, getString(R.string.compare_menu_dimensions)))
        entries.add(PieEntry(FORDONSDATA, getString(R.string.compare_menu_vehicle)))

        dataSet = PieDataSet(entries, "")
        with(dataSet) {
            sliceSpace = 3f
            selectionShift = 5f
            colors = kotlin.collections.arrayListOf(android.graphics.Color.parseColor("#4FE3C1"))
            valueTextSize = 16f
        }
    }

    private fun initPieData() {
        pieData = PieData(dataSet)
        pieData.setValueTextSize(0f)
    }

    private fun initPieChart() {
        with(piechart_menu) {
            data = pieData
            description.isEnabled = false
            legend?.isEnabled = false
            setHoleColor(Color.TRANSPARENT)
            animateY(1000, Easing.EasingOption.EaseInQuad)
        }
        piechart_menu.setOnChartValueSelectedListener(this)
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        when (e?.y) {
            TEKNISK -> mainActivity().selected = TEKNISK
            DIMENSIONER -> mainActivity().selected = DIMENSIONER
            FORDONSDATA -> mainActivity().selected = FORDONSDATA
        }
        navigate(R.id.action_menuFragment_to_baseCompareFragment)
    }

    override fun onNothingSelected() {}

}
