package com.example.benjo.bil_app_kotlin.base


import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragmentHome : Fragment() {

    abstract fun layoutId(): Int




    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?):
            View? = inflater.inflate(layoutId(), container, false)


    fun showText(textId: Int) {
        Snackbar.make(this.view!!, textId, Snackbar.LENGTH_LONG).show()
    }

    override fun getContext(): Context = activity!!.applicationContext


}
