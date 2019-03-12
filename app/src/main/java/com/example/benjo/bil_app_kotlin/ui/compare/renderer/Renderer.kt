package com.example.benjo.bil_app_kotlin.ui.compare.renderer

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.ItemModel


abstract class Renderer<M : ItemModel, VH : androidx.recyclerview.widget.RecyclerView.ViewHolder> {

    var type = -1

    abstract fun bindView(model: M, holder: VH)
    abstract fun createViewHolder(parent: ViewGroup?): VH

    class Type {
        companion object {
            const val ENVIRONMENT = 10
            const val COMMON = 20
            const val TECH_OTHER = 30
            const val VEHICLE = 40
        }
    }

}