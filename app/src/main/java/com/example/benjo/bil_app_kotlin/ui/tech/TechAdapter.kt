package com.example.benjo.bil_app_kotlin.ui.tech

import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.ui.tab.Row

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlinx.android.synthetic.main.item_tabs_expandable_row.view.*
import kotlinx.android.synthetic.main.item_tabs_row.view.*

class TechAdapter(private val title: String?,
                  private val list: List<Row>,
                  private val sectionAdapter: SectionedRecyclerViewAdapter) : StatelessSection(SectionParameters
        .builder()
        .itemResourceId(R.layout.item_tabs_row)
        .headerResourceId(R.layout.item_tabs_expandable_row)
        .build()) {


    private var expanded = true

    override fun getContentItemsTotal(): Int = if (expanded) list.size else 0

    /* -------------------------------------- Parent related ------------------------------------- */
    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        val parentHolder = holder as TechParentViewHolder
        parentHolder.title?.text = title
        parentHolder.rootView?.setOnClickListener {
            onExpansionClick(parentHolder)
            sectionAdapter.notifyDataSetChanged()
        }
    }

    private fun onExpansionClick(holder: TechAdapter.TechParentViewHolder) {
        expanded = !expanded
        when (expanded) {
            true -> holder.image?.setImageResource(R.drawable.ic_arrow_up)
            false -> holder.image?.setImageResource(R.drawable.ic_arrow_down)
        }
    }

    override fun getHeaderViewHolder(view: View?): RecyclerView.ViewHolder = TechParentViewHolder(view)


    class TechParentViewHolder(view: View?) : RecyclerView.ViewHolder(view!!) {
        val rootView = view?.expandable_header
        val title = view?.header_text
        val image = view?.header_img
    }
    /* ------------------------------------------------------------------------------------------- */


    /* -------------------------------------- Child related -------------------------------------- */
    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        with(holder as TechChildViewHolder) {
            descRow?.text = list[position].desc
            dataRow?.text = list[position].data
        }
    }

    override fun getItemViewHolder(view: View?): RecyclerView.ViewHolder = TechChildViewHolder(view)

    class TechChildViewHolder(childView: View?) : RecyclerView.ViewHolder(childView!!) {
        val descRow = childView?.row_tv_desc
        val dataRow = childView?.row_tv_data
    }
    /* ------------------------------------------------------------------------------------------- */


}