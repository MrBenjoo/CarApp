package com.example.benjo.bil_app_kotlin.saved


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager

import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.home.HomeActivity
import com.example.benjo.bil_app_kotlin.adapters.AdapterSavedList
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import kotlinx.android.synthetic.main.fragment_saved.*
import kotlinx.android.synthetic.main.dialog_delete_car.view.*
import android.view.*
import com.example.benjo.bil_app_kotlin.base.BaseFragmentHome
import com.example.benjo.bil_app_kotlin.utils.builder.FragmentToolbar
import com.example.benjo.bil_app_kotlin.utils.builder.ToolbarManager


class SavedFragment : BaseFragmentHome(), SavedContract.View {
    private val TAG = "SavedFragment"
    private var adapterSavedList: AdapterSavedList? = null
    private var carList = arrayListOf<CarData>()
    private var carMultiList = arrayListOf<CarData>()
    private var isMultiSelected = false
    private var mActionMode: ActionMode? = null
    override lateinit var presenter: SavedContract.Presenter
    override fun layoutId(): Int = R.layout.fragment_saved


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is HomeActivity) presenter = context.savedPresenter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        ToolbarManager(builder(), view).prepareToolbar()
    }

    private fun builder(): FragmentToolbar = FragmentToolbar.Builder()
            .withId(R.id.toolbar_saved)
            .withNavBackListener({ activity!!.onBackPressed() }, R.id.nav_back_saved)
            .build()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initAdapter()
        initRecyclerView()
    }

    private fun initAdapter() {
        adapterSavedList = AdapterSavedList(
                { rowItem: CarData, position: Int -> itemClicked(rowItem, position) },
                { rowItem: CarData, position: Int -> itemLongClicked(rowItem, position) })
    }

    private fun initRecyclerView() = with(fragment_saved_list) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(activity?.applicationContext)
        adapter = adapterSavedList
    }

    override fun onResume() {
        super.onResume()
        presenter.loadSavedCars()
    }

    override fun updateView(list: ArrayList<CarData>) {
        carList = list
        adapterSavedList?.setList(list)
    }

    override fun showCar(car: CarData) {
        val intent = Intent(context, TabsActivity::class.java)
        intent.putExtra("jsonResult", car.json)
        activity?.startActivity(intent)
    }

    private fun itemClicked(rowItem: CarData, position: Int) {
        if (isMultiSelected) multiSelect(position)
        else presenter.getCarFromDB(rowItem.vin)
    }

    private fun itemLongClicked(rowItem: CarData, position: Int): Boolean {
        prepareView()
        multiSelect(position)
        return true
    }

    private fun prepareView() {
        if (!isMultiSelected) {
            isMultiSelected = true
            adapterSavedList!!.setMulti(isMultiSelected)
            carMultiList = arrayListOf()

            if (mActionMode == null)
                mActionMode = activity!!.startActionMode(SavedActionMode(this))
        }
    }

    private fun multiSelect(position: Int) {
        if (mActionMode != null) {
            carMultiList = presenter.onMultipleClick(carMultiList, carList, position)
            if (carMultiList.size > 0) mActionMode?.title = carMultiList.size.toString()
            else mActionMode?.title = ""
            refreshAdapter()
        }
    }

    override fun onDestroyActionMode() {
        mActionMode = null
        isMultiSelected = false
        adapterSavedList!!.setMulti(isMultiSelected)
        carMultiList = arrayListOf()
        refreshAdapter()
    }

    private fun refreshAdapter() = with(adapterSavedList!!) {
        setMultiList(carMultiList)
        setList(carList)
        notifyDataSetChanged()
    }

    override fun showDialogOnMultipleDeletion() {
        val deleteDialogView = getDialogView()
        val dialog = AlertDialog.Builder(activity!!).create()
        dialog.setView(deleteDialogView)
        deleteDialogView?.tv_saved_dialog_yes?.setOnClickListener {
            onDialogYesClickMultipleDeletion()
            dialog.cancel()
        }
        deleteDialogView?.tv_saved_dialog_cancel?.setOnClickListener { dialog.cancel() }
        dialog.show()
    }

    private fun getDialogView(): View? = LayoutInflater
            .from(activity)
            .inflate(R.layout.dialog_delete_car, null)


    private fun onDialogYesClickMultipleDeletion() {
        if (carMultiList.size > 0) {
            for (i in carMultiList.indices) {
                carList.remove(carMultiList[i])
                presenter.deleteCarFromDB(carMultiList[i].vin)
            }

            adapterSavedList?.notifyDataSetChanged()
            mActionMode!!.finish()
            presenter.loadSavedCars()

        }
    }

}
