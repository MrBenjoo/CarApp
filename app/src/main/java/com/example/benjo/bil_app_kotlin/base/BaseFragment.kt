package com.example.benjo.bil_app_kotlin.base

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.R
import kotlinx.android.synthetic.main.fragment_base.*

abstract class BaseFragment : Fragment() {
    private val TAG = "BaseFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }


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
        (activity as MainActivity).showText(text)
    }

    fun showProgess() {
        progessBar.visibility = View.VISIBLE
    }

    fun hideProgress() {
        progessBar.visibility = View.GONE
    }

    fun showErrorHTTP() {
        with(activity as MainActivity) {
            showText(resources.getString(R.string.error_http_code))
        }
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(TAG, "onDetach")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "onDestroyView")
    }
}