package com.example.benjo.bil_app_kotlin.ui.saved

import androidx.recyclerview.widget.DiffUtil
import android.util.Log
import com.example.benjo.bil_app_kotlin.data.db.model.CarData

class MyDiffUtil(private val oldList: ArrayList<CarData>,
                 private val newList: ArrayList<CarData>) : DiffUtil.Callback() {

    init {
        Log.d("MyDiffUtil", "init...")
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val sameItems = oldList[oldItemPosition].vin == newList[newItemPosition].vin
        Log.d("MyDiffUtil", "areItemsTheSame, oldItemPosition = $oldItemPosition, newItemPosition = $newItemPosition ->" + sameItems.toString())
        return sameItems
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val same = oldList[oldItemPosition].isChecked == newList[newItemPosition].isChecked
        Log.d("MyDiffUtil", "areContentsTheSame, oldItemPosition = $oldItemPosition, newItemPosition = $newItemPosition -> " + same.toString())
        return same
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

}