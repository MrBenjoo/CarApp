package com.example.benjo.bil_app_kotlin.ui.tech

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.data.network.model.Result
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

interface TechContract {

    interface ViewTech : BaseView<TechPresenter> {
        fun setAdapter(adapter: SectionedRecyclerViewAdapter)
        fun getTechnicalTitle(): String
        fun getDimensionsTitle(): String
        fun getOtherTitle(): String
    }

    interface TechPresenter : BasePresenter<ViewTech> {
        fun bind()
        fun updateTab(result: Result?)
    }

}