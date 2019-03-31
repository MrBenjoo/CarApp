package com.example.benjo.swecar


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController


class MainActivity : AppCompatActivity() {
    var selected: Float = 0F
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavigationFragment).navigateUp()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(App.localeManager.setLocale(newBase!!))
    }

}
