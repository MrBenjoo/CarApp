package com.example.benjo.bil_app_kotlin.ui.tab


import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView

import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.ui.basic.BasicAdapter
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import retrofit2.Response

interface TabsContract {


    /* Tab Related */
    interface ViewTabs : BaseView<TabsPresenter> {
        fun internetOn()
        fun internetOff()
        fun showResponseCode(code: Int)
        fun updateResult(result : Result)
        fun showMsgCarSaved()
        fun showMsgCarAlreadySaved()
        fun isComparing() : Boolean
        fun showCompareView(result: Result)
        fun setProgressVisible()
        fun setProgressInvisible()
        fun onCloseSearchCompare() : Boolean
    }

    interface TabsPresenter {
        fun search(reg: String?)
        fun onImgSaveClicked(result : Result?)
    }
    /* ----------- */


    /* Technical tab related */
    interface ViewTech : BaseView<TechPresenter> {
        fun setAdapter(adapter: SectionedRecyclerViewAdapter)
    }

    interface TechPresenter : BasePresenter<ViewTech>, Presenter {
        fun bind()
    }
    /* -------------------- */


    /* Basic Tab related */
    interface ViewBasic : BaseView<BasicPresenter> {
        fun setAdapter(adapter : BasicAdapter)
    }

    interface BasicPresenter : BasePresenter<ViewBasic>, Presenter {
        fun bind()
    }
    /* ------------------*/


    interface Presenter {
        fun updateTab(result: Result?)
    }



}