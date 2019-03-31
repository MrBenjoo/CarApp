package com.example.benjo.swecar.ui.saved

import androidx.recyclerview.widget.DiffUtil
import com.example.benjo.swecar.data.db.model.CarData

class SavedDiffUitl(private val oldList: ArrayList<CarData>,
                    private val newList: ArrayList<CarData>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].vin == newList[newItemPosition].vin
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isChecked == newList[newItemPosition].isChecked
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

}