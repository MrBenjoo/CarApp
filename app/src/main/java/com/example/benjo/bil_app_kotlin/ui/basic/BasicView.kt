package com.example.benjo.bil_app_kotlin.ui.basic


import android.content.Context
import android.os.Bundle
import android.view.View
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.base.BaseFragment
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.utils.mainActivity
import com.example.benjo.bil_app_kotlin.utils.showText
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class BasicView : BaseFragment(), BasicContract.View {
    private val TAG = "BasicView"
    override lateinit var presenter: BasicContract.Presenter
    override fun layoutId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = BasicPresenter(BasicAdapter(arrayListOf()))
    }

    @Subscribe
    fun onEventResult(searchResponse: SearchResponse?) {
        presenter.updateTab(searchResponse)
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.updateTab(mainActivity().searchResponseCar1)
    }

    override fun showTextMalformedJson() {
        showText(R.string.error_malformed_json)
    }
}
