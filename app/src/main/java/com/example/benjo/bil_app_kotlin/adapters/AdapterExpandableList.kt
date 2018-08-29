package com.example.benjo.bil_app_kotlin.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.tabs.Row
import com.example.benjo.bil_app_kotlin.tabs.tech.TechChildVH
import com.example.benjo.bil_app_kotlin.tabs.tech.TechParentVH
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

class AdapterExpandableList(private val title: String?,
                            private val list: List<Row>,
                            private val sectionAdapter: SectionedRecyclerViewAdapter) : StatelessSection(SectionParameters
        .builder()
        .itemResourceId(R.layout.item_row_view)
        .headerResourceId(R.layout.expandable_header_view)
        .build()) {

    internal var expanded = true


    override fun getContentItemsTotal(): Int = if (expanded) list.size else 0 //return expanded ? list.size() : 0


    /* -------------------------------------- Child related -------------------------------------- */
    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        with(holder as TechChildVH) {
            descRow?.text = list[position].desc
            dataRow?.text = list[position].data
        }
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return TechChildVH(view)
    }
    /* ------------------------------------------------------------------------------------------- */


    /* -------------------------------------- Parent related ------------------------------------- */
    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        with(holder as TechParentVH) {
            headerText?.text = title
            rootView?.setOnClickListener {
                expanded = !expanded
                if (expanded) {
                    imgView?.setImageResource(R.drawable.ic_arrow_up)
                } else {
                    imgView?.setImageResource(R.drawable.ic_arrow_down)
                }
                sectionAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return TechParentVH(view)
    }
    /* ------------------------------------------------------------------------------------------- */

}