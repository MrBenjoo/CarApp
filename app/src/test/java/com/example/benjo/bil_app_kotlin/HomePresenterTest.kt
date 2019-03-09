package com.example.benjo.bil_app_kotlin

import com.example.benjo.bil_app_kotlin.data.network.CarService
import com.example.benjo.bil_app_kotlin.data.network.model.SearchResponse
import com.example.benjo.bil_app_kotlin.ui.home.HomeContract
import com.example.benjo.bil_app_kotlin.ui.home.HomePresenter
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Deferred
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HomePresenterTest {
    private val carService : CarService = mockk()
    private val view : HomeContract.View = mockk()
    private val presenter = HomePresenter(carService)

    @Before
    fun setupMocks() {
        presenter.attachView(view)
    }

    @Test
    fun `search for a registration with valid return response`() {
        val REG = "RON810"

        val response : Deferred<Response<Exception("test")>> = mockk()

        every { carService.searchReg(REG) } returns Exception("test")
    }


}