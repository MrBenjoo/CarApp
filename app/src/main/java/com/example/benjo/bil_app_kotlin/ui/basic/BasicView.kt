package com.example.benjo.bil_app_kotlin.ui.basic


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class BasicView : BaseFragment(), TabsContract.ViewBasic {
    private val TAG = "BasicView"
    override lateinit var presenter: TabsContract.BasicPresenter
    override fun layoutId(): Int = R.layout.fragment_base

    init {
        EventBus.getDefault().register(this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = BasicPresenter(BasicAdapter(arrayListOf()))
    }

    @Subscribe
    fun onEventResult(result : Result?) {
        presenter.updateTab(result)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.bind()
    }

    override fun setAdapter(adapter: BasicAdapter) {
        recyclerview_base.setHasFixedSize(true)
        recyclerview_base.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        val result = (activity as MainActivity).resultCar1
        presenter.updateTab(result)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


}
