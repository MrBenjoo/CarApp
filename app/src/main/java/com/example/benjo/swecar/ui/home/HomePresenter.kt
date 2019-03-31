package com.example.benjo.swecar.ui.home

import com.example.benjo.swecar.data.Result
import com.example.benjo.swecar.data.network.ApiHelper
import com.example.benjo.swecar.data.network.model.SearchResponse
import com.example.benjo.swecar.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class HomePresenter(private var apiHelper: ApiHelper,
                    private val viewmodel : MainViewModel) : HomeContract.Presenter, CoroutineScope {
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


    private fun processResponseBody(body: SearchResponse?) {
        when (body != null) {
            true -> {
                viewmodel.setSearchResponse(body)
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