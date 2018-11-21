package com.example.benjo.bil_app_kotlin


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.benjo.bil_app_kotlin.ui.comparing.Compare
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.utils.Constants.Companion.EVENT_GET_COMPARE_DATA
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe


class MainActivity : AppCompatActivity() {
    var selected: Float = 0F
    var resultCar1: Result? = null
    lateinit var compare: Compare

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.mainNavigationFragment).navigateUp()

    @Subscribe
    fun onEventResult(result: Result?) {
        resultCar1 = result
    }

    @Subscribe
    fun onEvent(message: String) = when (message) {
        EVENT_GET_COMPARE_DATA -> EventBus.getDefault().post(compare)
        else -> Unit
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}
