package com.example.benjo.bil_app_kotlin.ui.home

import com.example.benjo.bil_app_kotlin.data.network.ApiHelper
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import kotlinx.coroutines.*
import org.greenrobot.eventbus.EventBus
import kotlin.coroutines.CoroutineContext
import com.example.benjo.bil_app_kotlin.data.Result
import retrofit2.Response

class HomePresenter(private var apiHelper: ApiHelper) : HomeContract.Presenter, CoroutineScope {
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
            val searched = searchReg(reg)

            when (searched) {
                is Result.Value -> handleResponse(searched.value)
                is Result.Error -> view.showExceptionError(searched.error)
            }
        }
    }

    private suspend fun searchReg(reg: String?): Result<Exception, Response<SearchResponse>> {
        return try {
            view.showProgress()
            Result.build { apiHelper.searchRegAsync(reg).await() }
        } catch (e: Exception) {
            Result.build { throw e }
        } finally {
            view.hideProgress()
        }
    }

    private fun handleResponse(response: Response<SearchResponse>) {
        when (response.isSuccessful) { // 200..300 OK
            true -> processResponseBody(response.body())
            false -> handleResponseCodes(response.code())
        }
    }

    /**
     * EventBus sends data to MainActivity so that Basic- and TechnicalView can
     * retrieve it in their onResume method.
     */
    private fun processResponseBody(body: SearchResponse?) {
        when (body != null) {
            true -> {
                EventBus.getDefault().post(body)
                view.navigateToTabs()
            }
            false -> view.showServerError()
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

}