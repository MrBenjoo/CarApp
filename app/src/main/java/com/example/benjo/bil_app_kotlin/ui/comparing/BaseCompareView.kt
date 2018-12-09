package com.example.benjo.bil_app_kotlin.ui.comparing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ItemModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererAdapter
import kotlinx.android.synthetic.main.fragment_base.*


abstract class BaseCompareView : Fragment() {
    lateinit var compare: Compare
    lateinit var adapterRenderer: RendererAdapter
    var carOneModel: String? = null
    var carTwoModel: String? = null
    var listOfItems = arrayListOf<ItemModel>()

    abstract fun initListItems()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?):
            View? {
        setHasOptionsMenu(true)
        compare = (activity as MainActivity).compare
        adapterRenderer = RendererAdapter()
        carOneModel = compare.carOneData?.carModel
        carTwoModel = compare.carTwoData?.carModel
        initListItems()
        adapterRenderer.setItems(listOfItems)
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview_base.adapter = adapterRenderer
    }

    fun string(id: Int): String {
        return context?.resources?.getString(id) ?: "N/A"
    }

    override fun onPause() {
        super.onPause()
        /* It will keep adding new items to the list if not cleared
       (Need to understand FragmentPageAdapter better) */
        if (listOfItems.isNotEmpty()) listOfItems.clear()
    }




}