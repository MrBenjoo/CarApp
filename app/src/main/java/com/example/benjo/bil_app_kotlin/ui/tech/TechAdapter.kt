package com.example.benjo.bil_app_kotlin.ui.tech

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.data.model.Row

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlinx.android.synthetic.main.view_expandable_header.view.*
import kotlinx.android.synthetic.main.view_row_tabs.view.*

class TechAdapter(private val title: String?,
                  private val list: List<Row>,
                  private val sectionAdapter: SectionedRecyclerViewAdapter) : StatelessSection(SectionParameters
        .builder()
        .itemResourceId(R.layout.view_row_tabs)
        .headerResourceId(R.layout.view_expandable_header)
        .build()) {

    internal var expanded = true


    override fun getContentItemsTotal(): Int = if (expanded) list.size else 0


    /* -------------------------------------- Child related -------------------------------------- */
    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        with(holder as TechChildViewHolder) {
            descRow?.text = list[position].desc
            dataRow?.text = list[position].data
        }
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder {
        return TechChildViewHolder(view)
    }

    class TechChildViewHolder(childView: View?) : RecyclerView.ViewHolder(childView!!) {
        val descRow = childView?.row_tv_desc
        val dataRow = childView?.row_tv_data
    }
    /* ------------------------------------------------------------------------------------------- */


    /* -------------------------------------- Parent related ------------------------------------- */
    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        with(holder as TechParentViewHolder) {
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
        return TechParentViewHolder(view)
    }

    class TechParentViewHolder(headerView: View?) : RecyclerView.ViewHolder(headerView!!) {
        val rootView = headerView?.expandable_header
        val headerText = headerView?.header_text
        val imgView = headerView?.header_img
    }
    /* ------------------------------------------------------------------------------------------- */


}