package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.util.SparseArray
import com.example.benjo.bil_app_kotlin.ui.comparing.model.ItemModel

class RendererAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listOfItems = arrayListOf<ItemModel>()
    private var arrayOfRenderer = SparseArray<Renderer<*, *>>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val renderer = arrayOfRenderer[viewType]
        if (renderer != null) return renderer.createViewHolder(parent)
        throw RuntimeException("Not supported Item View Type: $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listOfItems[position]
        val renderer = arrayOfRenderer[item.getType()] as? Renderer<ItemModel, RecyclerView.ViewHolder>
        if (renderer != null) renderer.bindView(item, holder)
        else throw RuntimeException("Not supported View Holder: $holder")
    }

    fun registerRenderer(renderer: Renderer<*, *>) {
        val mType = renderer.type
        if (arrayOfRenderer[mType] == null) arrayOfRenderer.put(mType, renderer)
        else throw RuntimeException("Renderer already exist with this type: " + mType)
    }

    fun setItems(items: ArrayList<ItemModel>) {
        listOfItems.clear()
        listOfItems.addAll(items)
    }

    override fun getItemViewType(position: Int): Int {
        return listOfItems[position].getType()
    }

    override fun getItemCount(): Int = listOfItems.size

}