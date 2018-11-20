package com.example.benjo.bil_app_kotlin.ui.comparing.renderer

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.util.SparseArray


/*
Renderer<ItemModel, RecyclerView.ViewHolder> is not working as I thought it would. The problem is that the
array will only be bound to objects of ItemModel and RecyclerView.ViewHolder and nothing else, even if for example
a model object inherits from ItemModel. The following compiler error will be produced:
    Type mismatch:
        - Required Renderer<ItemModel, RecyclerView.ViewHolder>
        - Found: SomeViewRendererOne()
This happens after I write rendererRecyclerViewAdapter.registerRenderer(SomeViewRendererOne()) where SomeViewRendererOne
inherits from Renderer<SomeModelOne, SomeViewHolder>().
Solution was to add the star projection type * in the parameters and allow the array to accept any kind of values.
But after doing that renderer.bindView(item, holder) was giving another compiler error. This was because it doesn't know
what generic type the renderer is because it can contain multiple ViewRenderers for different viewholders. It knew before
when I wrote Renderer<ItemModel, RecyclerView.ViewHolder> because I explicitly told the types. All I needed to do
was cast it to Renderer<ItemModel, RecyclerView.ViewHolder>
https://stackoverflow.com/questions/41102225/kotlins-generics-type-mismatch-in-generic-map-parameter
 */
class RendererAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var listOfItems = arrayListOf<ItemModel>()
    //private var renderer: Renderer<ItemModel, RecyclerView.ViewHolder>? = null
    private var arrayOfRenderers = SparseArray<Renderer<*, *>>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val renderer = arrayOfRenderers[viewType]
        if (renderer != null) {
            return renderer.createViewHolder(parent)
        }
        throw RuntimeException("Not supported Item View Type: $viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = listOfItems[position]
        val renderer = arrayOfRenderers[item.getType()] as? Renderer<ItemModel, RecyclerView.ViewHolder>
        if (renderer != null) renderer.bindView(item, holder)
        else throw RuntimeException("Not supported View Holder: $holder")
    }

    fun registerRenderer(renderer: Renderer<*, *>) {
        val mType = renderer.type
        if (arrayOfRenderers[mType] == null) {
            arrayOfRenderers.put(mType, renderer)
        } else {
            throw RuntimeException("Renderer already exist with this type: " + mType);
        }
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