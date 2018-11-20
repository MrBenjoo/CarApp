package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup


abstract class Renderer<M : ItemModel, VH : RecyclerView.ViewHolder> {

    var type = -1

    abstract fun bindView(model: M, holder: VH)
    abstract fun createViewHolder(parent: ViewGroup?): VH

}