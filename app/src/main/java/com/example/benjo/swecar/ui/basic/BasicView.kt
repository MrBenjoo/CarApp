package com.example.benjo.swecar.ui.basic


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.benjo.swecar.R
import com.example.benjo.swecar.base.BaseFragment
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.utils.showText
import kotlinx.android.synthetic.main.fragment_base.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class BasicView : BaseFragment(), BasicContract.View {
    private val TAG = "BasicView"
    override lateinit var presenter: BasicContract.Presenter
    override fun layoutId(): Int = R.layout.fragment_base

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = BasicPresenter(BasicAdapter(arrayListOf()), ViewModelProviders.of(activity!!).get(MainViewModel::class.java))
    }

    /**
     * Gets called from TabsPresenter when searching for a new registration number and
     * the app is not in a compare mode state.
     */
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
        //presenter.updateTab(mainActivity().searchResponseCar1)
        presenter.updateTabTest()
    }

    override fun showTextMalformedJson() {
        showText(R.string.error_malformed_json)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
