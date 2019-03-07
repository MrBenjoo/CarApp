package com.example.benjo.bil_app_kotlin.ui.compare.tabs


import com.example.benjo.bil_app_kotlin.ui.compare.CompareBaseView
import com.example.benjo.bil_app_kotlin.ui.compare.CompareContract
import com.example.benjo.bil_app_kotlin.ui.compare.SelectedPageEvent
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.RENDERER_TYPE_COMMON
import com.example.benjo.bil_app_kotlin.ui.compare.renderer.RendererRowProgressbarView


class MotorView : CompareBaseView(), CompareContract.View {

    override fun initListItems() {
        val renderer = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }
        presenter.attachView(this)
        listOfItems = presenter.onPageSelected(SelectedPageEvent.OnTechnicalClick(titleMotor()))
        adapterRenderer.registerRenderer(renderer)
    }



}
