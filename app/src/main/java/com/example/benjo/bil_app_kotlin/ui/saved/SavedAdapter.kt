package com.example.benjo.bil_app_kotlin.ui.saved

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.EventData
import com.example.benjo.bil_app_kotlin.data.room.CarData
import kotlinx.android.synthetic.main.view_row_view_saved.view.*
import kotlinx.android.synthetic.main.view_row_with_cb.view.*
import org.greenrobot.eventbus.EventBus

class SavedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var carList = ArrayList<CarData>()
    var carActionModeList = ArrayList<CarData>()
    var isActionMode = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        1 -> viewActionMode(parent)
        else -> viewSavedCars(parent)
    }

    private fun viewActionMode(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderActionMode(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_row_with_cb, parent, false))

    private fun viewSavedCars(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderSavedCars(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_row_view_saved, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder.itemViewType) {
        1 -> {
            with(holder as ViewHolderActionMode) {
                val car = carList[position]
                reg.text = car.reg
                model.text = car.model
                modelYear.text = car.modelYear
                type.text = car.type
                cb.isChecked = car.isChecked
                bind(car)
            }
        }
        else -> {
            with(holder as ViewHolderSavedCars) {
                val car = carList[position]
                reg.text = car.reg
                model.text = car.model
                modelYear.text = car.modelYear
                type.text = car.type
                bind(car)
            }
        }
    }

    fun selectAll() {
        for(carData in carList) {
            carData.isChecked = true
        }
        notifyDataSetChanged()
    }

    fun unSelectAll() {
        for(carData in carList) {
            carData.isChecked = false
        }
        notifyDataSetChanged()
    }

    fun isAllSelected() : Boolean {
        for (car in carList) {
            if (!car.isChecked) return false
        }
        return true
    }

    override fun getItemViewType(position: Int): Int = if (isActionMode) 1 else 2

    override fun getItemCount(): Int = carList.size

    fun setList(list: ArrayList<CarData>) {
        this.carList.clear()
        this.carList.addAll(list)
        notifyDataSetChanged()
    }



    class ViewHolderSavedCars(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reg = itemView.tv_saved_reg
        val model = itemView.tv_saved_model
        val modelYear = itemView.tv_saved_year
        val type = itemView.tv_saved_type


        fun bind(row: CarData) {
            val eventData = EventData(row, adapterPosition)
            with(EventBus.getDefault()) {
                itemView.setOnClickListener { post(SavedListEvent.OnShortClick(eventData)) }
                itemView.setOnLongClickListener {
                    post(SavedListEvent.OnLongClick(eventData))
                    true
                }
            }
        }
    }

    class ViewHolderActionMode(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reg = itemView.row_saved_tv_reg_cb
        val model = itemView.row_saved_tv_model_cb
        val modelYear = itemView.row_saved_tv_model_year_cb
        val type = itemView.row_saved_tv_type_cb
        val cb = itemView.row_saved_cb

        fun bind(row: CarData) {

            val eventData = EventData(row, adapterPosition)
            with(EventBus.getDefault()) {
                itemView.row_saved_cb.setOnClickListener { post(SavedListEvent.OnShortClick(eventData)) }
                itemView.setOnClickListener { post(SavedListEvent.OnShortClick(eventData)) }
                itemView.setOnLongClickListener {
                    post(SavedListEvent.OnLongClick(eventData))
                    true
                }
            }
        }
    }

}