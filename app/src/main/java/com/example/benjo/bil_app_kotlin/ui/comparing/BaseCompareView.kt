package com.example.benjo.bil_app_kotlin.ui.comparing

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.ItemModel
import com.example.benjo.bil_app_kotlin.ui.comparing.renderer.RendererAdapter
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.EVENT_GET_COMPARE_DATA
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


abstract class BaseCompareView : Fragment() {
    var listOfItems = arrayListOf<ItemModel>()
    lateinit var compare: Compare
    lateinit var adapterRenderer: RendererAdapter
    var carOneModel: String? = null
    var carTwoModel: String? = null

    init {
        EventBus.getDefault().register(this)
    }

    abstract fun initListItems()

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        EventBus.getDefault().post(EVENT_GET_COMPARE_DATA)
    }

    @Subscribe
    fun onEvent(compare: Compare) {
        this.compare = compare
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?):
            View? {
        setHasOptionsMenu(true)
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

    /*
      If will keep adding new items to the list if not cleared
      (Need to understand FragmentPageAdapter better)
     */
    override fun onPause() {
        super.onPause()
        if (listOfItems.isNotEmpty()) listOfItems.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}