package com.example.benjo.bil_app_kotlin.sections.basic


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.benjo.bil_app_kotlin.tabview.MainActivity
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.sections.Row
import kotlinx.android.synthetic.main.fragment_base.*


class FragmentBasic : BaseFragment(), Contract.View {
    private val TAG = "FragmentBasic"
    private val arrayList = arrayListOf<Row>()
    private lateinit var listAdapter: ListAdapter

    override lateinit var presenter: Contract.Presenter

    override fun getContext(): Context {
        return activity!!.applicationContext
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter(arrayList)
        list.adapter = listAdapter
    }

    override fun onResume() {
        super.onResume()
        val result = activity!!.intent.getStringExtra("basic")
        presenter = (activity as MainActivity).presenterBasic
        if (result != null) {
            Log.d(TAG, "onResume -- result is NOT null")
            Log.d(TAG, result)
            presenter.update(result)
        }
    }

    override fun updateList(list: ArrayList<Row>) {
        listAdapter.setList(list)
    }

}
