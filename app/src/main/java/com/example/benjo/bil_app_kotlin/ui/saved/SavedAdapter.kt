package com.example.benjo.bil_app_kotlin.ui.saved

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.room.CarData
import kotlinx.android.synthetic.main.item_saved_row.view.*
import kotlinx.android.synthetic.main.item_saved_row_cb.view.*
import org.greenrobot.eventbus.EventBus

class SavedAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var carList = ArrayList<CarData>()
    var isActionMode = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        1 -> viewActionMode(parent)
        else -> viewSavedCars(parent)
    }

    private fun viewActionMode(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderActionMode(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_saved_row_cb, parent, false))

    private fun viewSavedCars(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderSavedCars(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_saved_row, parent, false))

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

    override fun getItemViewType(position: Int): Int = if (isActionMode) 1 else 2

    override fun getItemCount(): Int = carList.size

    fun setList(list: ArrayList<CarData>) {
        this.carList.clear()
        this.carList.addAll(list)
        notifyDataSetChanged()
    }

    fun updatePosition(carData: CarData, position: Int) {
        carList[position] = carData
        notifyItemChanged(position)
    }

    class ViewHolderSavedCars(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reg = itemView.tv_saved_reg
        val model = itemView.tv_saved_model
        val modelYear = itemView.tv_saved_year
        val type = itemView.tv_saved_type

        fun bind(row: CarData) {
            val eventData = EventData(row, adapterPosition)
            with(EventBus.getDefault()) {
                itemView.setOnClickListener {
                    post(SavedListEvent.OnShortClick(eventData))
                }
                itemView.setOnLongClickListener {
                    post(SavedListEvent.OnLongClick(eventData))
                    true
                }
            }
        }
    }

    class ViewHolderActionMode(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reg = itemView.tv_saved_reg_cb
        val model = itemView.tv_saved_model_cb
        val modelYear = itemView.tv_saved_year_cb
        val type = itemView.tv_saved_type_cb
        val cb = itemView.cb_saved
        val rootview = itemView.rootview_saved_row_cb


        fun bind(row: CarData) {

            val eventData = EventData(row, adapterPosition)
            with(EventBus.getDefault()) {

                rootview.setOnClickListener {
                    Log.d("SavedAdapter", "onClickListener")
                    post(SavedListEvent.OnShortClick(eventData))
                }
            }
        }
    }

}