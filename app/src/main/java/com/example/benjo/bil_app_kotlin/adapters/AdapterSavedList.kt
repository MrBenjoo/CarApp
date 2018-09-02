package com.example.benjo.bil_app_kotlin.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.room.CarData
import kotlinx.android.synthetic.main.item_row_view.view.*
import kotlinx.android.synthetic.main.item_row_with_cb.view.*

class AdapterSavedList(
        val clickListener: (CarData, Int) -> Unit,
        val longClickListener: (CarData, Int) -> Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var savedList = ArrayList<CarData>()
    private var savedMultiList = ArrayList<CarData>()
    private var multiSelected = false

    var fieldProperty: Int = 0

    var setterPropertyForField: Int
        get() = fieldProperty
        set(value) {
            fieldProperty = value
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        1 -> viewCb(parent)
        else -> viewSaved(parent)
    }

    private fun viewCb(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderCb(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_with_cb, parent, false))

    private fun viewSaved(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderSaved(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_view, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder.itemViewType) {
        1 -> {
            with(holder as ViewHolderCb) {
                descRowCb.text = savedList[position].id.toString()
                dataRowCb.text = savedList[position].vin.toString()
                holder.cb.isChecked = savedMultiList.contains(savedList[position])
                bind(savedList[position], clickListener, longClickListener)
            }
        }
        else -> {
            with(holder as ViewHolderSaved) {
                descRow.text = savedList[position].id.toString()
                dataRow.text = savedList[position].vin.toString()
                bind(savedList[position], clickListener, longClickListener)
            }
        }
    }

    fun setMulti(multi: Boolean) {
        multiSelected = multi
    }

    override fun getItemViewType(position: Int): Int = if (multiSelected) 1 else 2


    fun setMultiList(savedMultiList: ArrayList<CarData>) {
        this.savedMultiList = savedMultiList
    }

    override fun getItemCount(): Int {
        return savedList.size ?: 0
    }

    fun setList(list: ArrayList<CarData>) {
        this.savedList.clear()
        this.savedList.addAll(list)
        notifyDataSetChanged()
    }


    class ViewHolderSaved(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descRow = itemView.row_tv_desc
        val dataRow = itemView.row_tv_data


        fun bind(row: CarData, clickListener: (CarData, Int) -> Unit, longClickListener: (CarData, Int) -> Boolean) {
            itemView.setOnClickListener { clickListener(row, adapterPosition) }
            itemView.setOnLongClickListener { longClickListener(row, adapterPosition) }
        }
    }

    class ViewHolderCb(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val descRowCb = itemView.row_tv_desc_cb
        val dataRowCb = itemView.row_tv_data_cb
        val cb = itemView.row_cb

        fun bind(row: CarData, clickListener: (CarData, Int) -> Unit, longClickListener: (CarData, Int) -> Boolean) {
            itemView.setOnClickListener { clickListener(row, adapterPosition) }
            itemView.row_cb.setOnClickListener { clickListener(row, adapterPosition) }
            itemView.setOnLongClickListener { longClickListener(row, adapterPosition) }
        }
    }

}