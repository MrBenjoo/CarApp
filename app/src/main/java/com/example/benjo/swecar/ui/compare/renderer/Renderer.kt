package com.example.benjo.swecar.ui.compare.renderer

import android.view.ViewGroup
import com.example.benjo.swecar.ui.compare.data.model.ItemCompareModel


abstract class Renderer<M : ItemCompareModel, VH : androidx.recyclerview.widget.RecyclerView.ViewHolder> {

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