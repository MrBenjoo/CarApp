package com.example.benjo.bil_app_kotlin


import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.findNavController
import com.example.benjo.bil_app_kotlin.ui.comparing.Compare
import com.example.benjo.bil_app_kotlin.data.network.model.Result
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity() {
    var selected: Float = 0F
    var resultCar1: Result? = null
    lateinit var compare: Compare

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavigationFragment).navigateUp()

    override fun attachBaseContext(newBase: Context?) {
        Log.d("LifeCycles", "MainActivity -> attachBaseContext")
        super.attachBaseContext(App.localeManager.setLocale(newBase!!))
    }

    @Subscribe
    fun onEventResult(result: Result?) {
        resultCar1 = result
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }




}
