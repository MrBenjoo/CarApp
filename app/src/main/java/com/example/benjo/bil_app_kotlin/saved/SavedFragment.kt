package com.example.benjo.bil_app_kotlin.saved


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.home.HomeActivity
import com.example.benjo.bil_app_kotlin.tabview.sections.Row
import com.example.benjo.bil_app_kotlin.adapters.SavedListAdapter
import com.example.benjo.bil_app_kotlin.room.CarData
import com.example.benjo.bil_app_kotlin.tabview.TabsActivity
import kotlinx.android.synthetic.main.fragment_saved.*

class SavedFragment : Fragment(), SavedContract.View {

    override fun showCar(car: CarData) {
        val intent = Intent(context, TabsActivity::class.java)
        intent.putExtra("jsonResult", car.json)
        activity?.startActivity(intent)
    }

    private val arrayList = arrayListOf<Row>()
    //private var listAdapter: ListAdapter? = null
    private var savedListAdapter: SavedListAdapter? = null
    override lateinit var presenter: SavedContract.Presenter

    override fun showProgess() {

    }

    override fun hideProgress() {

    }

    override fun showErrorHTTP() {

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity) {
            presenter = (context).savedPresenter
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        frag_saved_toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //listAdapter = ListAdapter(arrayList)
        savedListAdapter = SavedListAdapter(arrayListOf<CarData>(), { rowItem : CarData -> itemClicked(rowItem) })
        with(fragment_saved_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            //adapter = listAdapter
            adapter = savedListAdapter
        }
       // listAdapter!!.clickListener = { row: Row -> itemClicked(row) }

    }

    override fun onResume() {
        super.onResume()
        presenter.loadSavedCars()
    }

    override fun updateView(list: ArrayList<CarData>) {
        savedListAdapter?.setList(list)
    }

    private fun itemClicked(rowItem: CarData) {
        Log.d("SavedFragment", "Clicked: ${rowItem.json}")
        presenter.getCarFromDB(rowItem.vin)
    }

    override fun getContext(): Context = activity!!.applicationContext

}
