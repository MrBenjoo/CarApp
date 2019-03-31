package com.example.benjo.swecar.ui.compare

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.R
import com.example.benjo.swecar.ui.compare.data.DataProvider
import com.example.benjo.swecar.ui.compare.data.model.ItemCompareModel
import com.example.benjo.swecar.ui.compare.renderer.RendererAdapter
import com.example.benjo.swecar.utils.string
import kotlinx.android.synthetic.main.fragment_base.*


abstract class CompareBaseView : androidx.fragment.app.Fragment() {
    lateinit var adapterRenderer: RendererAdapter
    lateinit var presenter: CompareContract.Presenter
    var listOfItems = arrayListOf<ItemCompareModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = ComparePresenter(DataProvider(ViewModelProviders.of(activity!!).get(MainViewModel::class.java)))
    }

    abstract fun initListItems()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?):
            View? {

        setHasOptionsMenu(true)
        adapterRenderer = RendererAdapter()
        initListItems()
        adapterRenderer.setItems(listOfItems)
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerview_base.adapter = adapterRenderer
    }

    override fun onPause() {
        super.onPause()
        /* It will keep adding new items to the list if not cleared
       (Need to understand FragmentPageAdapter better) */
        if (listOfItems.isNotEmpty()) listOfItems.clear()
    }

    override fun getContext(): Context = activity!!.applicationContext

    fun titleEnvironment(): String = string(R.string.compare_tab_environment)

    fun titleMotor() = string(R.string.compare_tab_motor)

    fun titleWeights(): String = string(R.string.compare_tab_weights)

    fun titleOther(): String = string(R.string.compare_tab_other)

    fun titleWheels(): String = string(R.string.compare_tab_wheels)

}