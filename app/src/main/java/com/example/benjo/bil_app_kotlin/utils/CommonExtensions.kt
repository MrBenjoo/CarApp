package com.example.benjo.bil_app_kotlin.utils

import android.graphics.Rect
import android.view.TouchDelegate
import android.view.View
import androidx.annotation.Px

/**
 * Expands the View's touch area in order to make the view's clickable functionality more user
 * friendly (following the guidelines for material design).
 *
 * This function operates with pixels. To convert dp to pixels this formula is used:
 * value * resources.displayMetrics.density).toInt()
 *
 * To get a better understanding of how the function works, see this link:
 * https://stackoverflow.com/questions/22589322/what-does-top-left-right-and-bottom-mean-in-android-rect-object
 *
 * @param value how much to expand the view in pixels.
 * @param parentView needs to be used to expand the child view.
 */
fun View.increaseTouchArea(parentView: View, @Px value: Int) {
    val childView = this
    parentView.post {
        val childRect = Rect()
        val parentRect = Rect()

        childView.getHitRect(childRect)
        parentView.getHitRect(parentRect)

        childRect.left -= value
        childRect.top -= value
        childRect.right += value
        childRect.bottom += value

        parentView.touchDelegate = TouchDelegate(childRect, childView)
    }
}