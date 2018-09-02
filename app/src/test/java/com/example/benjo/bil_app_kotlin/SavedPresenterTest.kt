package com.example.benjo.bil_app_kotlin

import com.example.benjo.bil_app_kotlin.saved.SavedContract
import com.example.benjo.bil_app_kotlin.saved.SavedFragment
import com.example.benjo.bil_app_kotlin.saved.SavedPresenter
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SavedPresenterTest {
    private lateinit var presenter : SavedContract.Presenter

    @Mock private lateinit var view : SavedFragment

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = SavedPresenter()
        presenter.attachView(view)
    }

}