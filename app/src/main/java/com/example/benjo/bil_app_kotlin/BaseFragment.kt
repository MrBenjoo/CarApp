package com.example.benjo.bil_app_kotlin


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.list.ListAdapter
import com.example.benjo.bil_app_kotlin.list.Row
import kotlinx.android.synthetic.main.fragment_base.*


class BaseFragment : Fragment() {
    private val arrayList = arrayListOf<Row>()
    private var listAdapter: ListAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initList()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initList() {
        listAdapter = ListAdapter(arrayList)
        with(list) {
            setHasFixedSize(true)
            layoutManager = android.support.v7.widget.LinearLayoutManager(activity?.applicationContext)
            adapter = listAdapter
        }
    }

    fun updateList(list: ArrayList<Row>) {
        listAdapter?.setList(list)
    }


}
