package com.example.benjo.bil_app_kotlin.ui.comparing


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.model.Compare
import com.example.benjo.bil_app_kotlin.data.model.Result
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import com.example.benjo.bil_app_kotlin.utils.JsonCompare
import com.example.benjo.bil_app_kotlin.utils.MenuHandler
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.google.gson.GsonBuilder
import java.lang.Exception


class MenuFragment : BaseFragment(), OnChartValueSelectedListener {
    private val TAG = "MenuFragment"

    companion object {
        val DIMENSIONER = 1.001F
        val TEKNISK = 1.002F
        val ÖVRIGT = 1.003F
    }

    private lateinit var navController: NavController

    override fun layoutId(): Int = R.layout.fragment_menu

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MenuHandler(view, this)
        navController = Navigation.findNavController(view)
        (activity as HomeActivity).compare = with(MenuFragmentArgs.fromBundle(arguments)) {
            setupCompareJson(firstJson, secondJson)!!
        }
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
        else -> Unit
    }


    override fun onNothingSelected() {}

}
