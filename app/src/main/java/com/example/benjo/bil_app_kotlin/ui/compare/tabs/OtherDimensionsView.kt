package com.example.benjo.bil_app_kotlin.ui.compare.tabs

import com.example.benjo.bil_app_kotlin.ui.compare.CompareBaseView
import com.example.benjo.bil_app_kotlin.ui.compare.CompareContract
import com.example.benjo.bil_app_kotlin.ui.compare.SelectedPageEvent
import com.example.benjo.bil_app_kotlin.ui.compare.renderer.RendererRowProgressbarView
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON

class OtherDimensionsView : CompareBaseView(), CompareContract.View {
    private val TAG = "OtherDimensionsView"

    override fun initListItems() {
        val rendererCommon = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }
        presenter.attachView(this)
        listOfItems = presenter.onPageSelected(SelectedPageEvent.OnDimensionsClick(titleOther()))
        adapterRenderer.registerRenderer(rendererCommon)
    }

}