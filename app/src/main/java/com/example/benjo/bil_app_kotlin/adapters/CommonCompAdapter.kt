package com.example.benjo.bil_app_kotlin.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.ComparableData
import com.example.benjo.bil_app_kotlin.ui.comparing.CommonViewHolder


class CommonCompAdapter(val listOfComparableData: ArrayList<ComparableData>) : RecyclerView.Adapter<CommonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CommonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_row_common, parent, false)
        return CommonViewHolder(view)
    }

    override fun getItemCount(): Int = listOfComparableData.size


    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) = with(listOfComparableData) {
        val title = this[position].title
        holder.title.text = title
        holder.carModelOne.text = this[position].carModelOne ?: "N/A"
        holder.carOneData.text = this[position].carOneData ?: "N/A"
        holder.carModelTwo.text = this[position].carModelTwo ?: "N/A"
        holder.carTwoData.text = this[position].carTwoData ?: "N/A"
        normalizeOn(title, holder, position)
    }

    private fun normalizeOn(title: String, holder: CommonViewHolder, position: Int) {
        val carOneData = listOfComparableData[position].carOneData
        val carTwoData = listOfComparableData[position].carTwoData
        when (title) {
            "Length" -> {
                holder.barCarOne.progress = normalizationLength(carOneData)
                holder.barCarTwo.progress = normalizationLength(carTwoData)
            }
            "Width" -> {
                holder.barCarOne.progress = normalizationWidth(carOneData)
                holder.barCarTwo.progress = normalizationWidth(carTwoData)
            }
            "Height" -> {
                holder.barCarOne.progress = normalizationHeight(carOneData)
                holder.barCarTwo.progress = normalizationHeight(carTwoData)
            }
            else -> Unit
        }
    }

    private fun normalizationLength(dimension: String?): Int = when (dimension != null) {
        true -> (((dimension!!.toInt()) / 5000F) * 100).toInt()
        false -> 0
    }

    private fun normalizationWidth(dimension: String?): Int = when (dimension != null) {
        true -> (((dimension!!.toInt()) / 2200F) * 100).toInt()
        false -> 0
    }

    private fun normalizationHeight(dimension: String?): Int = when (dimension != null) {
        true -> (((dimension!!.toInt()) / 4000F) * 100).toInt()
        false -> 0
    }


}