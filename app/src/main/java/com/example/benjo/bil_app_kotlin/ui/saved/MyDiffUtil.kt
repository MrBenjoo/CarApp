package com.example.benjo.bil_app_kotlin.ui.saved

import android.support.v7.util.DiffUtil
import android.util.Log
import com.example.benjo.bil_app_kotlin.data.db.model.CarData

class MyDiffUtil(private val oldList: ArrayList<CarData>,
                 private val newList: ArrayList<CarData>) : DiffUtil.Callback() {

    init {
        Log.d("MyDiffUtil", "init...")
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val sameItems = oldList[oldItemPosition].vin == newList[newItemPosition].vin
        Log.d("MyDiffUtil", "areItemsTheSame -> " + sameItems.toString())
        return sameItems
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val same = oldList[oldItemPosition] == newList[newItemPosition]
        Log.d("MyDiffUtil", "areContentsTheSame -> " + same.toString())
        return same
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

}