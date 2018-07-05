package com.example.benjo.bil_app_kotlin

import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import android.view.ViewGroup

class SectionsPageAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
    private val fragmentList = arrayListOf<Fragment?>()
    private val titleList = arrayListOf<String>()



    fun addFragment(fragment: Fragment?, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList[position]
    }

    override fun getItem(position: Int): Fragment? {
        /*Log.d("SectionPageAdapter", "getItem $position")
        when (position) {
            0 -> return BaseFragment()
            1 -> return BaseFragment()
            2 -> return BaseFragment()
            else -> return null
        }*/
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
       // return 3
    }



   /* override fun instantiateItem(container: ViewGroup, position: Int): Any {
        Log.d("SectionPageAdapter", "instantiateItem $position")
        val fragment = super.instantiateItem(container, position) // calling getItem
        if(fragment is BaseFragment) {
            when (position) {
                0 -> {
                    Log.d("SectionPageAdapter", "Fordonsdata")
                }
                1 -> {
                    Log.d("SectionPageAdapter", "Teknisk data")
                }
                2 -> {
                    Log.d("SectionPageAdapter", "Historik")
                }
            }
        }
        return fragment
    }*/


}


