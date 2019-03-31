package com.example.benjo.swecar.ui.compare.tabs


import com.example.benjo.swecar.ui.compare.CompareBaseView
import com.example.benjo.swecar.ui.compare.CompareContract
import com.example.benjo.swecar.ui.compare.SelectedPageEvent
import com.example.benjo.swecar.utils.Constants.Companion.RENDERER_TYPE_COMMON
import com.example.benjo.swecar.ui.compare.renderer.RendererRowProgressbarView


class MotorView : CompareBaseView(), CompareContract.View {

    override fun initListItems() {
        val renderer = RendererRowProgressbarView().also { it.type = RENDERER_TYPE_COMMON }
        presenter.attachView(this)
        listOfItems = presenter.onPageSelected(SelectedPageEvent.OnTechnicalClick(titleMotor()))
        adapterRenderer.registerRenderer(renderer)
    }



}
