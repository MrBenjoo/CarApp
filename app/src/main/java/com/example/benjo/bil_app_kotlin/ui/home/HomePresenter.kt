package com.example.benjo.bil_app_kotlin.ui.home

import android.util.Log
import com.example.benjo.bil_app_kotlin.data.network.CarService
import com.example.benjo.bil_app_kotlin.data.network.model.Result
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext


class HomePresenter(val carService : CarService) : HomeContract.Presenter, CoroutineScope {
    private val TAG = "HomePresenter"
    private lateinit var view: HomeContract.View
    private var jobTracker: Job = Job()

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun search(reg: String?) {
         launch {
             async { searchReal(reg) }.await()
         }

        //searchFake() // test...
    }


    private fun searchFake() {
        val jsonCarOne = CommonUtils().loadJSONFromAsset(view.getContext(), "bil_1.json")
        val result = GsonBuilder().create().fromJson(jsonCarOne, Result::class.java)
        EventBus.getDefault().post(result) // saves the result in MainActivity and updates Basic- and TechView list
        view.navigateToTabs()
    }

    private suspend fun searchReal(reg: String?): Result? {
        view.showProgress()
        var result: Result? = null
        val response = carService.searchReg(reg).await()
        if (response.isSuccessful) {
            result = response.body()
            if (result != null) {
                EventBus.getDefault().post(result) // saves the result in MainActivity and update Basic- and TechView list
                view.navigateToTabs()
            } else Log.d(TAG, "searchReal($reg) -> result == null")
        } else view.showResponseCode(response.code())
        view.hideProgress()
        return result
    }

    override fun cancelJob() {
        jobTracker.cancel()
    }

}