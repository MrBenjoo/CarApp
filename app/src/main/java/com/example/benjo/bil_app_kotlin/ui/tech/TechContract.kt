package com.example.benjo.bil_app_kotlin.ui.tech

import com.example.benjo.bil_app_kotlin.base.BasePresenter
import com.example.benjo.bil_app_kotlin.base.BaseView
import com.example.benjo.bil_app_kotlin.domain.Result
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter

interface TechContract {

    interface ViewTech : BaseView<TechPresenter> {
        fun setAdapter(adapter: SectionedRecyclerViewAdapter)
    }

    interface TechPresenter : BasePresenter<ViewTech> {
        fun bind()
        fun updateTab(result: Result?)
    }

}