package com.example.benjo.bil_app_kotlin.sections.basic


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.sections.Row
import kotlinx.android.synthetic.main.fragment_base.*


class FragmentBasic : BaseFragment(), Contract.View {
    private val arrayList = arrayListOf<Row>()
    private var listAdapter: ListAdapter? = null


    override lateinit var presenter: Contract.Presenter

    override fun getContext(): Context = activity!!.applicationContext

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter(arrayList)
        list.adapter = listAdapter
    }

    override fun updateList(list: ArrayList<Row>) {
        listAdapter?.setList(list)
    }

}
