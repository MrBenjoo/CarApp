package com.example.benjo.bil_app_kotlin.ui.comparing

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.CompareRowRenderer
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ItemModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererAdapter
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.constants.Companion.TYPE_COMMONN
import com.example.benjo.bil_app_kotlin.ui.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_base.*

abstract class CompareRow : Fragment() {
    lateinit var renderRecyclerAdapter: RendererAdapter
    lateinit var renderer: CompareRowRenderer
     var carModelOne : String? = null
     var carModelTwo: String? = null
    var list = arrayListOf<ItemModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ):
            View? {
        setHasOptionsMenu(true)
        renderRecyclerAdapter = RendererAdapter()
        renderer = CompareRowRenderer()
        renderer.type = TYPE_COMMONN
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        carModelOne = (activity as HomeActivity).resultCar1?.carInfo?.basic?.data?.model
        carModelTwo = (activity as HomeActivity).resultCar2?.carInfo?.basic?.data?.model
        initList()
        renderRecyclerAdapter.registerRenderer(renderer)
        Log.d("CompareRow", "onViewCreated")
        recyclerview_base.adapter = renderRecyclerAdapter
    }

    /**
     * clear list otherwise it will keep adding new items with the old ones.
     * Need to understand FragmentPageAdapter better...
     */
    override fun onPause() {
        super.onPause()
        list.clear()
    }

    abstract fun initList()

    fun string(id: Int): String? {
        return context?.resources?.getString(id) ?: "N/A"
    }
}