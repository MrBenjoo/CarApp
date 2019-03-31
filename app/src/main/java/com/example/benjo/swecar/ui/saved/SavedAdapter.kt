package com.example.benjo.swecar.ui.saved


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.example.benjo.swecar.R
import com.example.benjo.swecar.data.db.model.CarData
import kotlinx.android.synthetic.main.item_saved_row.view.*
import kotlinx.android.synthetic.main.item_saved_row_cb.view.*
import org.greenrobot.eventbus.EventBus


class SavedAdapter(private val carList: ArrayList<CarData>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    var isActionMode = false

    override fun getItemViewType(position: Int): Int = if (isActionMode) 1 else 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder = when (viewType) {
        1 -> viewActionMode(parent)
        else -> viewDefault(parent)
    }

    private fun viewActionMode(parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder =
            ViewHolderActionMode(LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_saved_row_cb, parent, false))

    private fun viewDefault(parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return ViewHolderDefault(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_saved_row, parent, false))
    }

    override fun getItemCount(): Int = carList.size

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) = when (holder.itemViewType) {
        1 -> {
            with(holder as ViewHolderActionMode) {
                val car = carList[position]
                reg.text = car.reg
                model.text = car.model
                modelYear.text = car.modelYear
                type.text = car.type
                checkbox.isChecked = car.isChecked
                bind(car)
            }
        }
        else -> {
            with(holder as ViewHolderDefault) {
                val car = carList[position]
                reg.text = car.reg
                model.text = car.model
                modelYear.text = car.modelYear
                type.text = car.type
                bind(car)
            }
        }
    }


    fun updateList(newList: ArrayList<CarData>) {
        val result = DiffUtil.calculateDiff(SavedDiffUitl(carList, newList))
        result.dispatchUpdatesTo(this)
        carList.clear()
        carList.addAll(newList)
    }


    class ViewHolderDefault(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val reg = itemView.tv_saved_reg
        val model = itemView.tv_saved_model
        val modelYear = itemView.tv_saved_year
        val type = itemView.tv_saved_type

        fun bind(row: CarData) {
            val eventData = RowEventData(row, adapterPosition)
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

    class ViewHolderActionMode(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val reg = itemView.tv_saved_reg_cb
        val model = itemView.tv_saved_model_cb
        val modelYear = itemView.tv_saved_year_cb
        val type = itemView.tv_saved_type_cb
        val checkbox = itemView.cb_saved
        val rootview = itemView.rootview_saved_row_cb


        fun bind(row: CarData) {
            val eventData = RowEventData(row, adapterPosition)
            with(EventBus.getDefault()) {
                rootview.setOnClickListener { post(SavedListEvent.OnShortClick(eventData)) }
                checkbox.setOnClickListener { post(SavedListEvent.OnShortClick(eventData)) }
            }
        }
    }

}