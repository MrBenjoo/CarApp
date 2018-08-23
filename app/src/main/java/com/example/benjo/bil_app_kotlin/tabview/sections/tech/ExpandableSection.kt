package com.example.benjo.bil_app_kotlin.tabview.sections.tech

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.tabview.sections.Row
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection

class ExpandableSection(private val title: String?,
                        private val list: List<Row>,
                        private val sectionAdapter: SectionedRecyclerViewAdapter) : StatelessSection(SectionParameters
        .builder()
        .itemResourceId(R.layout.row_list)
        .headerResourceId(R.layout.header_list)
        .build()) {

    internal var expanded = true


    override fun getContentItemsTotal(): Int = if (expanded) list.size else 0 //return expanded ? list.size() : 0


    /* -------------------------------------- Child related -------------------------------------- */
    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        with(holder as ChildViewHolder) {
            descRow?.text = list[position].desc
            dataRow?.text = list[position].data
        }
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return ChildViewHolder(view)
    }
    /* ------------------------------------------------------------------------------------------- */


    /* -------------------------------------- Parent related ------------------------------------- */
    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        with(holder as ParentViewHolder) {
            headerText?.text = title
            rootView?.setOnClickListener {
                expanded = !expanded
                if (expanded) {
                    imgView?.setImageResource(R.drawable.ic_keyboard_arrow_up_24dp)
                } else {
                    imgView?.setImageResource(R.drawable.ic_keyboard_arrow_down_24dp)
                }
                sectionAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder {
        return ParentViewHolder(view)
    }
    /* ------------------------------------------------------------------------------------------- */

}