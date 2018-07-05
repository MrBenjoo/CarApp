package com.example.benjo.bil_app_kotlin

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_1
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_2
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_3
import com.example.benjo.bil_app_kotlin.expandable.TestFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private val TAG = "MainActivity"
    private var presenter: Presenter? = null
    private var sectionsPageAdapter: SectionsPageAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        initTabs()
        initPresenter()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        search_view.setOnQueryTextListener(this)
    }

    private fun initTabs() {
        tabs.setupWithViewPager(container)
        sectionsPageAdapter = SectionsPageAdapter(supportFragmentManager)
        container.adapter = sectionsPageAdapter
    }


    private fun initPresenter() {
        /*val map = HashMap<String, BaseFragment>()
        map[TITLE_TAB_1] = BaseFragment()
        map[TITLE_TAB_2] = BaseFragment()
        map[TITLE_TAB_3] = BaseFragment()*/


         /* Ta bort sen */
        val map = HashMap<String, TestFragment>()
        map[TITLE_TAB_1] = TestFragment()
        map[TITLE_TAB_2] = TestFragment()
        map[TITLE_TAB_3] = TestFragment()
        sectionsPageAdapter?.addFragment(map[TITLE_TAB_1], TITLE_TAB_1)
        sectionsPageAdapter?.addFragment(map[TITLE_TAB_2], TITLE_TAB_2)
        sectionsPageAdapter?.addFragment(map[TITLE_TAB_3], TITLE_TAB_3)


        /*addTab(map[TITLE_TAB_1], TITLE_TAB_1)
        addTab(map[TITLE_TAB_2], TITLE_TAB_2)
        addTab(map[TITLE_TAB_3], TITLE_TAB_3)*/
        presenter = Presenter(this, map) // ändra tbx map i presenter till basefragment
    }

    private fun addTab(fragment: BaseFragment?, title: String) {
        sectionsPageAdapter?.addFragment(fragment, title)
    }

    fun showText(text: String?) {
        Snackbar.make(findViewById<View>(android.R.id.content), text.toString(), Snackbar.LENGTH_LONG).show()
    }

    override fun onSupportNavigateUp(): Boolean {
        showText("onBackPressed")
        //onBackPressed();
        return true
    }

    fun internetOff() {
        showText("Ingen internetanslutning.")
        info_img_wifi_off.visibility = ImageView.VISIBLE
    }

    fun internetOn() {
        info_img_wifi_off.visibility = ImageView.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun onQueryTextSubmit(query: String?): Boolean = when (query?.length) {
        in 2..7 -> {
            search_view.onActionViewCollapsed()
            presenter?.search(query?.trim())
            true
        }
        else -> {
            showText("Antalet tecken måste vara mellan 2-7")
            false
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean = false
}
