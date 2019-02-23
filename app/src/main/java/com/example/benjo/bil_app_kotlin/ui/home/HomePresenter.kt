package com.example.benjo.bil_app_kotlin.ui.home

import android.util.Log
import com.example.benjo.bil_app_kotlin.data.network.CarService
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext
import com.example.benjo.bil_app_kotlin.data.Result
import retrofit2.Response


class HomePresenter(private var carService: CarService) : HomeContract.Presenter, CoroutineScope {
    private val TAG = "HomePresenter"
    private lateinit var view: HomeContract.View
    private var jobTracker: Job = Job()

    override fun attachView(view: HomeContract.View) {
        this.view = view
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + jobTracker

    override fun search(reg: String?) {
        launch { searchReal(reg) }

        //searchFake()
    }

    private suspend fun searchReal(reg: String?) {
        val searched = registrationSearch(reg)

        when (searched) {
            is Result.Value -> handleServerValue(searched.value)
            is Result.Error -> handleError(searched.error)
        }
    }

    private suspend fun registrationSearch(reg: String?): Result<Exception, Response<SearchResponse>> {
        return try {
            view.showProgress()
            Result.build { carService.searchReg(reg).await() }
        } catch (e: Exception) {
            Result.build { throw e }
        } finally {
            view.hideProgress()
        }
    }

    private fun handleServerValue(response: Response<SearchResponse>) {
        when (response.isSuccessful) { // 200..300 OK
            true -> processResponseBody(response.body())
            false -> handleResponseCodes(response.code())
        }
    }

    private fun handleError(error: Exception) {
        view.showExceptionError(error)
    }

    private fun processResponseBody(body: SearchResponse?) {
        when (body != null) {
            true -> {
                EventBus.getDefault().post(body)
                view.navigateToTabs()
            }
            false -> {
                Log.d(TAG, "processResponseBody -> body == null")
            }
        }
    }

    private fun handleResponseCodes(code: Int) {
        when (code) {
            in 400..500 -> view.showClientError()
            in 500..600 -> view.showServerError()
        }
    }

    override fun cancelJob() {
        jobTracker.cancel()
    }

    private fun searchFake() {
        val jsonCarOne = CommonUtils().loadJSONFromAsset(view.getContext(), "bil_1.json")
        val result = GsonBuilder().create().fromJson(jsonCarOne, SearchResponse::class.java)
        EventBus.getDefault().post(result) // saves the result in MainActivity and updates Basic- and TechView list
        view.navigateToTabs()
    }


}