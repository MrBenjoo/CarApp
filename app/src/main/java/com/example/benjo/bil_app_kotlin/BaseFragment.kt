package com.example.benjo.bil_app_kotlin

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_base.*

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initList() {
        /* 'init list adapter here' */
        with(list) {
            setHasFixedSize(true)
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity?.applicationContext)
            /* 'set adapter here' */
        }
    }

}