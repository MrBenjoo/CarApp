package com.example.benjo.bil_app_kotlin


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.View
import android.widget.ImageView
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_1
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_2
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_3
import com.example.benjo.bil_app_kotlin.list.expandable.ExpandableFragment
import com.example.benjo.bil_app_kotlin.list.ordinary.BasicListFragment
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
        val map = HashMap<String, Fragment>()
        map[TITLE_TAB_1] = BasicListFragment()
        map[TITLE_TAB_2] = ExpandableFragment()
        map[TITLE_TAB_3] = BasicListFragment()
        sectionsPageAdapter?.addFragment(map[TITLE_TAB_1], TITLE_TAB_1)
        sectionsPageAdapter?.addFragment(map[TITLE_TAB_2], TITLE_TAB_2)
        sectionsPageAdapter?.addFragment(map[TITLE_TAB_3], TITLE_TAB_3)
        presenter = Presenter(this, map)
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
        showText(resources.getString(R.string.error_no_internet))
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
            showText(resources.getString(R.string.error_reg_limit))
            false
        }
    }

    override fun onQueryTextChange(newText: String?): Boolean = false
}
