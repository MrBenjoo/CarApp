package com.example.benjo.bil_app_kotlin.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import com.example.benjo.bil_app_kotlin.R
import kotlinx.android.synthetic.main.fragment_base.*

abstract class BaseFragment : Fragment() {
    private val TAG = "BaseFragment"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?):
            View? = inflater.inflate(R.layout.fragment_base, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        super.onViewCreated(view, savedInstanceState)
    }


    private fun initList() {
        /* init list adapter here */
        with(list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            /* set adapter here */
        }
    }

    fun showText(text: String?) {
        (activity as TabsActivity).showText(text)
    }

}