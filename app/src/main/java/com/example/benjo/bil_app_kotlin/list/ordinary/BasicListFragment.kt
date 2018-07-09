package com.example.benjo.bil_app_kotlin.list.ordinary


import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.BaseFragment
import com.example.benjo.bil_app_kotlin.list.model.Row
import kotlinx.android.synthetic.main.fragment_base.*


class BasicListFragment : BaseFragment() {
    private val arrayList = arrayListOf<Row>()
    private var listAdapter: ListAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listAdapter = ListAdapter(arrayList)
        list.adapter = listAdapter
    }

    fun updateList(list: ArrayList<Row>) {
        listAdapter?.setList(list)
    }


}
