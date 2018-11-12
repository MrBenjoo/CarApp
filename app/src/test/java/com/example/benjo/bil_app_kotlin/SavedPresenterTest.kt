package com.example.benjo.bil_app_kotlin

import com.example.benjo.bil_app_kotlin.data.model.EventData
import com.example.benjo.bil_app_kotlin.data.repository.CarRepository
import com.example.benjo.bil_app_kotlin.data.room.CarData
import com.example.benjo.bil_app_kotlin.ui.saved.SavedAdapter
import com.example.benjo.bil_app_kotlin.ui.saved.SavedContract
import com.example.benjo.bil_app_kotlin.ui.saved.SavedListEvent
import com.example.benjo.bil_app_kotlin.ui.saved.SavedPresenter
import io.mockk.*
import org.junit.Before
import org.junit.Test


class SavedPresenterTest {

    private val adapter: SavedAdapter = mockk(relaxed = true)

    private val repository: CarRepository = mockk()

    private val view: SavedContract.View = mockk(relaxed = true)

    private lateinit var presenter: SavedPresenter

    val vin = "10"

    private val carData = CarData(1,
            "RON810",
            "s80",
            "2007",
            "PB",
            vin,
            "test")

    val longClickEvent =  SavedListEvent.OnLongClick(EventData(carData, 0))
    val shortClickEvent =  SavedListEvent.OnShortClick(EventData(carData, 0))

    @Before
    fun build() {
        presenter = SavedPresenter(repository, adapter)
        presenter.attachView(view)
    }

    @Test
    fun onLongClick() {
        presenter.onEvent(longClickEvent)
        verify { view.startActionMode() }
    }

    @Test
    fun onShortClickA() {
        every { repository.getCar(vin) } returns carData
        presenter.onEvent(shortClickEvent)
        verify { repository.getCar(vin) }
        verify { view.showCar(carData) }
    }

    @Test
    fun onShortClickB() {
        every { repository.getCar(vin) } returns null
        presenter.onEvent(shortClickEvent)
        verify { repository.getCar(vin) }
        verify { view wasNot Called }
    }




}

