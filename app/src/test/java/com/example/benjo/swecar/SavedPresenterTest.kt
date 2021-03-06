package com.example.benjo.swecar

import com.example.benjo.swecar.data.db.model.CarData
import com.example.benjo.swecar.data.db.repository.CarRepository
import com.example.benjo.swecar.ui.saved.*
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test


class SavedPresenterTest {

    private val adapter: SavedAdapter = mockk()

    private val repository: CarRepository = mockk()

    private val savedVM : SavedViewModel = mockk()

    private val view: SavedContract.View = mockk()


    private lateinit var presenter: SavedPresenter

    val vin = "10"

    private val carData = CarData(1,
            "RON810",
            "s80",
            "2007",
            "PB",
            vin,
            "test")

    val longClickEvent =  SavedListEvent.OnLongClick(RowEventData(carData, 0))
    val shortClickEvent =  SavedListEvent.OnShortClick(RowEventData(carData, 0))

    @Before
    fun build() {
        presenter = SavedPresenter(repository, adapter, savedVM)
        presenter.attachView(view)
    }

    @Test
    fun onLongClick() {
        presenter.onEvent(longClickEvent)
        verify { view.startActionMode() }
    }

    @Test
    fun onShortClickA() {
        /*
        every { repository.getCar(vin) } returns carData
        presenter.onEvent(shortClickEvent)
        verify { repository.getCar(vin) }
        verify { view.navigateToTabs(carData) }
        */
    }

    @Test
    fun onShortClickB() {
        /*every { repository.getCar(vin) } returns null
        presenter.onEvent(shortClickEvent)
        verify { repository.getCar(vin) }
        verify { view wasNot Called }
        */
    }




}

