package com.example.benjo.bil_app_kotlin.ui.compare.renderer

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.util.SparseArray
import com.example.benjo.bil_app_kotlin.ui.compare.data.model.ItemModel

class RendererAdapter : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    private var listOfItemModel = arrayListOf<ItemModel>()
    private var arrayOfRenderer = SparseArray<Renderer<*, *>>()

    override fun getItemViewType(position: Int): Int = listOfItemModel[position].getType()

    /**
     * Calls the createViewHolder method in a specific view that is defined by the view type.
     * The view type is obtained from the getItemViewType method above.
     * If no view type is found, throw RuntimeException -> Not supported Item View Type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        val renderer = arrayOfRenderer[viewType]
        if (renderer != null) return renderer.createViewHolder(parent)
        throw RuntimeException("Not supported Item View Type: $viewType")
    }

    /**
     * Store a renderer so that we later can call it's methods (bindView & onCreateViewHolder) for
     * different view types.
     */
    fun registerRenderer(renderer: Renderer<*, *>) {
        val mType = renderer.type
        if (arrayOfRenderer[mType] == null) arrayOfRenderer.put(mType, renderer)
        else throw RuntimeException("Renderer already exist with this type: $mType")
    }

    /**
     * Calls the bindView method in a specific view (that is defined by the view type and is
     * obtained from the listOfItemModel).
     */
    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val itemModel = listOfItemModel[position]
        val renderer = arrayOfRenderer[itemModel.getType()] as? Renderer<ItemModel, androidx.recyclerview.widget.RecyclerView.ViewHolder>
        if (renderer != null) renderer.bindView(itemModel, holder)
        else throw RuntimeException("Not supported View Holder: $holder")
    }

    /**
     * Set data to listOfItemModel so that we can obtain the view type that is called in
     * method getItemViewType and onBindViewHolder.
     */
    fun setItems(items: ArrayList<ItemModel>) {
        listOfItemModel.clear()
        listOfItemModel.addAll(items)
    }

    override fun getItemCount(): Int = listOfItemModel.size

}