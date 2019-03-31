package com.example.benjo.swecar.ui.tab

class TabsAdapter(fm: androidx.fragment.app.FragmentManager) : androidx.fragment.app.FragmentPagerAdapter(fm) {
    private val fragmentList = arrayListOf<androidx.fragment.app.Fragment>()
    private val titleList = arrayListOf<String>()


    fun addFragment(fragment: androidx.fragment.app.Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? = titleList[position]

    override fun getItem(position: Int): androidx.fragment.app.Fragment = fragmentList[position]

    override fun getCount(): Int = fragmentList.size

}


