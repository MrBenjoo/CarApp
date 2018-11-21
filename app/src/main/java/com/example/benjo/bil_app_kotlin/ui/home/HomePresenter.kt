package com.example.benjo.bil_app_kotlin.ui.home

import android.util.Log
import com.example.benjo.bil_app_kotlin.domain.Result
import com.example.benjo.bil_app_kotlin.domain.SearchRegProvider
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext


class HomePresenter : HomeContract.Presenter, CoroutineScope {
    private val TAG = "HomePresenter"
    private lateinit var view : HomeContract.View
    private var jobTracker: Job = Job()

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun search(reg: String?) {
       /* launch {
            async { searchReal(reg) }.await()
        }*/

        processResponse(null) // test...

    }

    private fun processResponse(body: Result?) {
        /* ------------------------------------------------------------- Används endast för att testa UI -------------------------------------------------------------*/
        val jsonCarOne = CommonUtils().loadJSONFromAsset(view.getContext(), "bil_1.json")
        val result = GsonBuilder().create().fromJson(jsonCarOne, Result::class.java)
        EventBus.getDefault().post(result) // saves the result in MainActivity and updates Basic- and TechView list
        view.navigateToTabs()
    }

    private suspend fun searchReal(reg: String?): Result? {
        var result: Result? = null
        val request = SearchRegProvider.provideSearchReg().searchReg(reg)
        val response = request.await()
        if (response.isSuccessful) {
            result = response.body()
            if (result != null) {
                EventBus.getDefault().post(result) // saves the result in MainActivity and updates Basic- and TechView list
                view.navigateToTabs()
            } else Log.d(TAG, "searchReal($reg) -> result == null")
        } else view.showResponseCode(response.code())
        return result
    }

}