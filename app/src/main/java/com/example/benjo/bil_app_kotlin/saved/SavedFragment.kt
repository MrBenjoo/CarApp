package com.example.benjo.bil_app_kotlin.saved


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.home.HomeActivity
import com.example.benjo.bil_app_kotlin.sections.Row
import com.example.benjo.bil_app_kotlin.sections.basic.ListAdapter
import kotlinx.android.synthetic.main.fragment_base.*

class SavedFragment : Fragment(), SavedContract.View {
    private val arrayList = arrayListOf<Row>()
    private var listAdapter: ListAdapter? = null
    override lateinit var presenter: SavedContract.Presenter

    override fun showProgess() {

    }

    override fun hideProgress() {

    }

    override fun showErrorHTTP() {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("SavedFragment", "onCreateView -> R.layout.fragment_base")
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if(context is HomeActivity) {
            presenter = (context as HomeActivity).savedPresenter
        }
        Log.d("SavedFragment", "onAttach -> " + context?.packageName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        listAdapter = ListAdapter(arrayList)
        with(list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = listAdapter
        }

        if (listAdapter == null) {
            Log.d("SavedFragment", "onActivityCreated -> listAdapter is null")
        } else {
            Log.d("SavedFragment", "onActivityCreated -> listAdapter is not null")
        }
    }

    override fun onResume() {
        super.onResume()
        if (listAdapter == null) {
            Log.d("SavedFragment", "onResume (start) -> listAdapter is null")
        } else {
            Log.d("SavedFragment", "onResume (start) -> listAdapter is not null")
        }

        presenter.loadSavedCars()

        if (listAdapter == null) {
            Log.d("SavedFragment", "onResume (end) -> listAdapter is null")
        } else {
            Log.d("SavedFragment", "onResume (end) -> listAdapter is not null")
        }
    }

    override fun updateView(list: ArrayList<Row>) {
        if (listAdapter == null) {
            Log.d("SavedFragment", "updateView -> listAdapter is null")
        } else {
            Log.d("SavedFragment", "updateView -> listAdapter is not null")

        }

        if (::presenter.isInitialized) {
            Log.d("SavedFragment", "updateView -> presenter isInitialized")
        } else {
            Log.d("SavedFragment", "updateView -> presenter isNotInitialized")
        }
        listAdapter?.setList(list)
    }

    override fun getContext(): Context = activity!!.applicationContext

}
