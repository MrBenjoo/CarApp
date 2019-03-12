package com.example.benjo.bil_app_kotlin

import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import com.example.benjo.bil_app_kotlin.data.db.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.network.ApiInterface
import com.example.benjo.bil_app_kotlin.data.network.ApiHelper
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.example.benjo.bil_app_kotlin.ui.tab.TabsPresenter
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import io.mockk.mockk
import okhttp3.mockwebserver.MockWebServer

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import okhttp3.mockwebserver.MockResponse
import org.junit.After
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
class TabsPresenterTest {

    private lateinit var server: MockWebServer
    private lateinit var adapter: ApiHelper
    private lateinit var jsonCar1 : String
    private lateinit var presenter : TabsContract.Presenter
    private val view : TabsContract.View = mockk()
    private val repository : CarRepository = mockk()

    @Before
    fun setup() {
        // Create a MockWebServer. These are lean enough that you can create a new
        // instance for every unit test.
        server = MockWebServer()

        // Start the server.
        server.start()

        // setup retrofit with fake baseUrl
        adapter = ApiHelper(Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(server.url("/").toString())
                .build()
                .create(ApiInterface::class.java))

        // used throughout the test to fake json response
        jsonCar1 = CommonUtils().loadJSONFromAsset(
                InstrumentationRegistry.getTargetContext(),
                "bil_1.json")

        presenter = TabsPresenter(view, repository)
    }


    @Test
    fun responseIsSuccessfulAndNotNull() {
        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(jsonCar1))

        val response = adapter.searchRegAsync("RON810").execute()
        val result = presenter.validateResponse(response)

        assertNotNull(result)
    }>

    @Test
    fun responseIsNotSuccessfulAndIsNull() {
        server.enqueue(MockResponse()
                .setResponseCode(404)
                .setBody(""))

        val response = adapter.searchRegAsync("RON810").execute()
        val result = presenter.validateResponse(response)

        assertNull(result)
    }


    @After
    fun shutdown() {
        // Shut down the server. Instances cannot be reused.
        server.shutdown()
    }

}
