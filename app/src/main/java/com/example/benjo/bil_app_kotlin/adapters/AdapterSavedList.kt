package com.example.benjo.bil_app_kotlin.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.room.CarData
import kotlinx.android.synthetic.main.item_row_view_saved.view.*
import kotlinx.android.synthetic.main.item_row_with_cb.view.*

class AdapterSavedList(
        val clickListener: (CarData, Int) -> Unit,
        val longClickListener: (CarData, Int) -> Boolean) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var savedList = ArrayList<CarData>()
    private var savedMultiList = ArrayList<CarData>()
    private var multiSelected = false


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = when (viewType) {
        1 -> viewCb(parent)
        else -> viewSaved(parent)
    }

    private fun viewCb(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderCb(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_with_cb, parent, false))

    private fun viewSaved(parent: ViewGroup): RecyclerView.ViewHolder =
            ViewHolderSaved(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_row_view_saved, parent, false))


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder.itemViewType) {
        1 -> {
            with(holder as ViewHolderCb) {
                reg.text = savedList[position].reg
                model.text = savedList[position].model
                modelYear.text = savedList[position].modelYear
                type.text = savedList[position].type
                holder.cb.isChecked = savedMultiList.contains(savedList[position])
                bind(savedList[position], clickListener, longClickListener)
            }
        }
        else -> {
            with(holder as ViewHolderSaved) {
                reg.text = savedList[position].reg
                model.text = savedList[position].model
                modelYear.text = savedList[position].modelYear
                type.text = savedList[position].type
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
        val reg = itemView.tv_saved_reg
        val model = itemView.tv_saved_model
        val modelYear = itemView.tv_saved_year
        val type = itemView.tv_saved_type


        fun bind(row: CarData, clickListener: (CarData, Int) -> Unit, longClickListener: (CarData, Int) -> Boolean) {
            itemView.setOnClickListener { clickListener(row, adapterPosition) }
            itemView.setOnLongClickListener { longClickListener(row, adapterPosition) }
        }
    }

    class ViewHolderCb(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reg = itemView.row_saved_tv_reg_cb
        val model = itemView.row_saved_tv_model_cb
        val modelYear = itemView.row_saved_tv_model_year_cb
        val type = itemView.row_saved_tv_type_cb
        val cb = itemView.row_saved_cb

        fun bind(row: CarData, clickListener: (CarData, Int) -> Unit, longClickListener: (CarData, Int) -> Boolean) {
            itemView.setOnClickListener { clickListener(row, adapterPosition) }
            itemView.row_saved_cb.setOnClickListener { clickListener(row, adapterPosition) }
            itemView.setOnLongClickListener { longClickListener(row, adapterPosition) }
        }
    }

}