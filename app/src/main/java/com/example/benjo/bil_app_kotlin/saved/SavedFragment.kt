package com.example.benjo.bil_app_kotlin.saved

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.home.HomeActivity
import com.example.benjo.bil_app_kotlin.adapters.SavedListAdapter
import com.example.benjo.bil_app_kotlin.room.CarData
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.android.synthetic.main.fragment_saved_dialog.view.*

class SavedFragment : Fragment(), SavedContract.View {
    private val TAG = "SavedFragment"
    private var savedListAdapter: SavedListAdapter? = null
    override lateinit var presenter: SavedContract.Presenter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity)
            presenter = context.savedPresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        frag_saved_toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        savedListAdapter = SavedListAdapter(arrayListOf<CarData>(),
                { rowItem: CarData -> itemClicked(rowItem) },
                { rowItem: CarData -> itemLongClicked(rowItem) })
        with(fragment_saved_list) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity?.applicationContext)
            adapter = savedListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadSavedCars()
    }

    override fun updateView(list: ArrayList<CarData>) {
        savedListAdapter?.setList(list)
    }

    override fun showCar(car: CarData) {
        val intent = Intent(context, TabsActivity::class.java)
        intent.putExtra("jsonResult", car.json)
        activity?.startActivity(intent)
    }

    private fun itemClicked(rowItem: CarData) {
        presenter.getCarFromDB(rowItem.vin)
    }

    private fun itemLongClicked(rowItem: CarData): Boolean {
        val deleteDialogView = getDialogView()
        val dialog = AlertDialog.Builder(activity!!).create()
        dialog.setView(deleteDialogView)
        deleteDialogView?.tv_saved_dialog_yes?.setOnClickListener {
            onDialogYesClick(rowItem)
            dialog.cancel()
        }
        deleteDialogView?.tv_saved_dialog_cancel?.setOnClickListener { dialog.cancel() }
        dialog.show()
        return true
    }

    private fun getDialogView(): View? = LayoutInflater
            .from(activity)
            .inflate(R.layout.fragment_saved_dialog, null)

    private fun onDialogYesClick(rowItem: CarData) {
        val deleted = presenter.deleteCarFromDB(rowItem.vin)
        if (deleted) showText(R.string.frag_save_car_deleted)
        else showText(R.string.frag_save_car_not_deleted)
        presenter.loadSavedCars()
    }

    fun showText(textId: Int) {
        Snackbar.make(this.view!!, textId, Snackbar.LENGTH_LONG).show()
    }

    override fun getContext(): Context = activity!!.applicationContext

}