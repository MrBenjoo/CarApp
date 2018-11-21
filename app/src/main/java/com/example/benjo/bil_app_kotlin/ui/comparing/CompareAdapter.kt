package com.example.benjo.bil_app_kotlin.ui.comparing

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class CompareAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private val fragmentList = arrayListOf<Fragment?>()
    private val titleList = arrayListOf<String>()


    fun addFragment(fragment: Fragment?, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? =
            titleList[position]


    override fun getItem(position: Int): Fragment? =
            fragmentList[position]


    override fun getCount(): Int = fragmentList.size

    fun contains(key: String): Boolean = titleList.contains(key)


}


