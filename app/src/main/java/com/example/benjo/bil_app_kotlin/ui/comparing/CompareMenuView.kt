package com.example.benjo.bil_app_kotlin.ui.comparing


import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.utils.JsonCompare
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.GsonBuilder
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

    private lateinit var navController: NavController

    override fun layoutId(): Int = R.layout.fragment_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDataSet()
        initPieData()
        initPieChart()
        navController = Navigation.findNavController(view)
        (activity as HomeActivity).compare = with(CompareMenuViewArgs.fromBundle(arguments)) {
            setupCompareJson(firstJson, secondJson)!!
        }
    }

    private fun initDataSet() {
        val entries = ArrayList<PieEntry>()

        entries.add(PieEntry(TEKNISK, "Tekniskdata"))
        entries.add(PieEntry(DIMENSIONER, "Dimensioner"))
        entries.add(PieEntry(FORDONSDATA, "Fordonsdata"))

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

    private fun setupCompareJson(firstJson: String, secondJson: String): Compare? {
        return JsonCompare().setupCompareJson(
                GsonBuilder().create().fromJson(firstJson, Result::class.java),
                GsonBuilder().create().fromJson(secondJson, Result::class.java))
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) = when (e?.y) {
        TEKNISK -> {
            (activity as HomeActivity).selected = TEKNISK
            navController.navigate(R.id.baseCompareFragment)
        }
        DIMENSIONER -> {
            (activity as HomeActivity).selected = DIMENSIONER
            navController.navigate(R.id.baseCompareFragment)
        }
        FORDONSDATA -> {
            (activity as HomeActivity).selected = FORDONSDATA
            navController.navigate(R.id.baseCompareFragment)
        }
        else -> Unit
    }


    override fun onNothingSelected() {}

}
