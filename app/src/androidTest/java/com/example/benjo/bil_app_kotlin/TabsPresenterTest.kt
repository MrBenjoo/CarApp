package com.example.benjo.bil_app_kotlin

import android.support.test.runner.AndroidJUnit4
import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import com.example.benjo.bil_app_kotlin.ui.tab.TabsContract
import com.example.benjo.bil_app_kotlin.ui.tab.TabsPresenter
import kotlinx.coroutines.experimental.runBlocking
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations


@RunWith(AndroidJUnit4::class)
class TabsPresenterTest {

    private lateinit var view : TabsContract.ViewTabs

    private lateinit var presenter : TabsContract.TabsPresenter

    @Mock
    private lateinit var repo : CarRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        view = TabsActivity()
        presenter = TabsPresenter(view, repo)
    }

    @Test
    fun addition_isCorrect() {
        var saved = false
        runBlocking {
            saved = presenter.saveToDatabase(10, "hej")
        }
        assertTrue(saved)
    }

}