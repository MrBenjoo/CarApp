package com.example.benjo.bil_app_kotlin.base

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.bil_app_kotlin.MainActivity

abstract class BaseFragment : Fragment() {
    private lateinit var viewForSnackbar: View

    abstract fun layoutId(): Int

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?):
            View? {
        setHasOptionsMenu(true)
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewForSnackbar = (activity as MainActivity).findViewById<View>(android.R.id.content)
    }

    fun showText(textID: Int) {
        Snackbar.make(viewForSnackbar, textID, Snackbar.LENGTH_LONG).show()
    }

    fun showText(text: String?) {
        Snackbar.make(viewForSnackbar, text.toString(), Snackbar.LENGTH_LONG).show()
    }

    override fun getContext(): Context = activity!!.applicationContext

}