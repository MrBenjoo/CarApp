package com.example.benjo.swecar.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benjo.swecar.MainActivity

abstract class BaseFragment : androidx.fragment.app.Fragment() {

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

    override fun getContext(): Context = activity!!.applicationContext

}