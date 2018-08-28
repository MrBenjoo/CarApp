package com.example.benjo.bil_app_kotlin.tabs.basic


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.adapters.ListAdapter
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.tabs.Row
import com.example.benjo.bil_app_kotlin.tabs.SectionsContract
import kotlinx.android.synthetic.main.fragment_base.*


class FragmentBasic : BaseFragment(), SectionsContract.ViewBasic {
    private val TAG = "FragmentBasic"
    private val arrayList = arrayListOf<Row>()
    private lateinit var listAdapter: ListAdapter
    override lateinit var presenter: SectionsContract.Presenter

    override fun getContext(): Context {
        return activity!!.applicationContext
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = (context as TabsActivity).presenterBasic
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter(arrayList)
        list.adapter = listAdapter
    }

    override fun onResume() {
        super.onResume()
        val jsonResult = activity!!.intent.getStringExtra("jsonResult")
        if (jsonResult != null) {
            presenter.update(jsonResult)
        }
    }

    override fun updateList(list: ArrayList<Row>) {
        listAdapter.setList(list)
    }

}