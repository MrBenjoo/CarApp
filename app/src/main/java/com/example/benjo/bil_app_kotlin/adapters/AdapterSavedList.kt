package com.example.benjo.bil_app_kotlin.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.room.CarData
import kotlinx.android.synthetic.main.item_row_view.view.*

class AdapterSavedList(var savedList: ArrayList<CarData>?,
                       val clickListener: (CarData) -> Unit,
                       val longClickListener: (CarData) -> Boolean) : RecyclerView.Adapter<AdapterSavedList.SavedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row_view, parent, false)
        return SavedViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedViewHolder, position: Int) {
        savedList?.let {
            with(holder) {
                descRow.text = it[position].id.toString()
                dataRow.text = it[position].vin.toString()
                bind(it[position], clickListener, longClickListener)
            }
        }
    }

    override fun getItemCount(): Int {
        return savedList?.size ?: 0
    }

    fun setList(list: ArrayList<CarData>) {
        this.savedList?.clear()
        this.savedList?.addAll(list)
        notifyDataSetChanged()
    }


    class SavedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descRow = itemView.row_tv_desc
        val dataRow = itemView.row_tv_data

        fun bind(row: CarData, clickListener: (CarData) -> Unit, longClickListener: (CarData) -> Boolean) {
            itemView.setOnClickListener { clickListener(row) }
            itemView.setOnLongClickListener { longClickListener(row) }

        }
    }

}