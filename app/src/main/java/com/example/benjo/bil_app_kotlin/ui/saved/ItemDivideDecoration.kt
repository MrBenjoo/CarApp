package com.example.benjo.bil_app_kotlin.ui.saved

import android.content.Context
import android.graphics.Canvas
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import com.example.benjo.bil_app_kotlin.R


class ItemDivideDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val divider = ResourcesCompat.getDrawable(context.resources, R.drawable.line_divider_list, null)

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if (divider != null) {
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            val childCount = parent.childCount

            for (i in 0 until childCount) {
                val child = parent.getChildAt(i)

                val params = child.layoutParams as RecyclerView.LayoutParams

                val top = child.bottom + params.bottomMargin
                val bottom = top + divider.intrinsicHeight

                divider.setBounds(left, top, right, bottom)

                if (parent.getChildAdapterPosition(child) == parent.adapter!!.itemCount - 1 && parent.bottom < bottom) { // this prevent a parent to hide the last item's divider
                    parent.setPadding(parent.paddingLeft, parent.paddingTop, parent.paddingRight, bottom - parent.bottom)
                }

                divider.draw(c)
            }
        }

    }
}