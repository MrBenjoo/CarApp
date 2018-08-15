package com.example.benjo.bil_app_kotlin.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.benjo.bil_app_kotlin.R
import com.example.benjo.bil_app_kotlin.saved.SavedContract
import com.example.benjo.bil_app_kotlin.saved.SavedPresenter


class HomeActivity : AppCompatActivity() {

    lateinit var presenter: HomeContract.Presenter
    lateinit var savedPresenter: SavedContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val homeFrag = HomeFragment()
        presenter = HomePresenter(homeFrag, this)
        homeFrag.presenter = presenter
        savedPresenter = SavedPresenter(applicationContext)
    }


    override fun onSupportNavigateUp() = findNavController(R.id.mainNavigationFragment).navigateUp()

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

}
