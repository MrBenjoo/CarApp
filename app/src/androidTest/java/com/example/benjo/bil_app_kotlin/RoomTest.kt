package com.example.benjo.bil_app_kotlin


import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.data.db.CarDataBase
import com.example.benjo.bil_app_kotlin.data.db.CarDataDao
import com.example.benjo.bil_app_kotlin.utils.CommonUtils
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException
import java.util.*


@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var database: CarDataBase
    private lateinit var dataDao: CarDataDao
    private lateinit var carData : CarData
    private lateinit var json : String


    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, CarDataBase::class.java).build()
        dataDao = database.carDataDao()
        json = CommonUtils().loadJSONFromAsset(context, "bil_1.json")
        carData = CarData(
                1,
                "RON810",
                "Volvo V50",
                "2007",
                "PB",
                "10",
                json)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertCarAndCheckIfInserted() {
        dataDao.insert(carData)
        val car = dataDao.getCar("10")
        assertThat(car, equalTo(carData))
    }

    @Test
    @Throws(Exception::class)
    fun insertCarThenDeleteCarAndThenCheckIfDeleted() {
        dataDao.insert(carData)
        dataDao.deleteCar("10")
        val car = dataDao.getCar("10")
        assertNull(car)
    }

    @Test
    @Throws(Exception::class)
    fun writeCarsAndReadInList() {
        val carData1 = CarData(1, "RON811", "volvo v50", "2007", "personbil", "10", json)
        val carData2 = CarData(1, "RON810", "volvo v50", "2007", "personbil", "11", json)
        dataDao.insert(carData1)
        dataDao.insert(carData2)
        val list = dataDao.getAll()
        for (car in list!!) {
            assertNotNull(car)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeCarsAndDeleteAll() {
        for (i in 1..10) {
            dataDao.insert(CarData(i.toLong(), "RON810", "volvo v50", "2007", "personbil", i.toString(), json))
        }
        val listBeforeDeletion = dataDao.getAll()
        assertTrue(listBeforeDeletion != null && listBeforeDeletion.isNotEmpty())
        dataDao.deleteAll()
        val listAfterDeletion = dataDao.getAll()
        assertTrue(listAfterDeletion == null)
    }

    @Test
    @Throws(Exception::class)
    fun getCarThatIsNotStoredInDatabase() {
        val carDb = dataDao.getCar((Random().nextInt(1000) + 10000).toString())
        //val carNull = CarData(null, null, null)
        assertTrue(carDb != null)
    }

}
