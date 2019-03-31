package com.example.benjo.swecar.ui.compare.tabs


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.example.benjo.swecar.R
import com.example.benjo.swecar.base.BaseFragment
import com.example.benjo.swecar.data.wheelsDataOne
import com.example.benjo.swecar.data.wheelsDataTwo
import com.example.benjo.swecar.ui.compare.CompareContract
import com.example.benjo.swecar.ui.compare.ComparePresenter
import com.example.benjo.swecar.MainViewModel
import com.example.benjo.swecar.ui.compare.data.DataProvider
import com.example.benjo.swecar.ui.compare.data.model.Compare
import kotlinx.android.synthetic.main.fragment_wheels.*

class WheelsView : BaseFragment(), CompareContract.View {
    private lateinit var compare: Compare
    private val TAG = "WheelsView"
    override lateinit var presenter: CompareContract.Presenter

    override fun layoutId(): Int = R.layout.fragment_wheels

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = ComparePresenter(DataProvider(ViewModelProviders.of(activity!!).get(MainViewModel::class.java)))
        presenter.attachView(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //presenter.onPageSelected(SelectedPageEvent.OnDimensionsClick("wheels"))
        //compare = (activity as MainActivity).compare
        //initView()
    }

    private fun initView() {
        val carOneData = compare.wheelsDataOne()
        val carTwoData = compare.wheelsDataTwo()
        val carOneModel = compare.carOneData?.carModel
        val carTwoModel = compare.carTwoData?.carModel

        tv_tire_model_1.text = carOneModel
        tv_tire_back_1.text = carOneData?.tireBack
        tv_tire_front_1.text = carOneData?.tireFront

        tv_tire_model_2.text = carTwoModel
        tv_tire_back_2.text = carTwoData?.tireBack
        tv_tire_front_2.text = carTwoData?.tireFront

        tv_rim_model_1.text = carOneModel
        tv_rim_back_1.text = carOneData?.rimBack
        tv_rim_front_1.text = carOneData?.rimFront

        tv_rim_model_2.text = carTwoModel
        tv_rim_back_2.text = carTwoData?.rimBack
        tv_rim_front_2.text = carTwoData?.rimFront
    }

    override fun titleWeights(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleOther(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleWheels(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleEnvironment(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun titleMotor(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}