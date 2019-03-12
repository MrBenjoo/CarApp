package com.example.benjo.bil_app_kotlin


import androidx.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import androidx.room.Room
import androidx.test.InstrumentationRegistry
import com.example.benjo.bil_app_kotlin.data.db.model.CarData
import com.example.benjo.bil_app_kotlin.data.db.RoomDb
import com.example.benjo.bil_app_kotlin.data.db.model.RoomDbDao
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

    private lateinit var database: RoomDb
    private lateinit var dataDaoRoom: RoomDbDao
    private lateinit var carData : CarData
    private lateinit var json : String


    @Before
    fun setup() {
        val context = InstrumentationRegistry.getTargetContext()
        database = Room.inMemoryDatabaseBuilder(context, RoomDb::class.java).build()
        dataDaoRoom = database.carDataDao()
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
        dataDaoRoom.insert(carData)
        val car = dataDaoRoom.getCar("10")
        assertThat(car, equalTo(carData))
    }

    @Test
    @Throws(Exception::class)
    fun insertCarThenDeleteCarAndThenCheckIfDeleted() {
        dataDaoRoom.insert(carData)
        dataDaoRoom.deleteCar("10")
        val car = dataDaoRoom.getCar("10")
        assertNull(car)
    }

    @Test
    @Throws(Exception::class)
    fun writeCarsAndReadInList() {
        val carData1 = CarData(1, "RON811", "volvo v50", "2007", "personbil", "10", json)
        val carData2 = CarData(1, "RON810", "volvo v50", "2007", "personbil", "11", json)
        dataDaoRoom.insert(carData1)
        dataDaoRoom.insert(carData2)
        val list = dataDaoRoom.getAll()
        for (car in list!!) {
            assertNotNull(car)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeCarsAndDeleteAll() {
        for (i in 1..10) {
            dataDaoRoom.insert(CarData(i.toLong(), "RON810", "volvo v50", "2007", "personbil", i.toString(), json))
        }
        val listBeforeDeletion = dataDaoRoom.getAll()
        assertTrue(listBeforeDeletion != null && listBeforeDeletion.isNotEmpty())
        dataDaoRoom.deleteAll()
        val listAfterDeletion = dataDaoRoom.getAll()
        assertTrue(listAfterDeletion == null)
    }

    @Test
    @Throws(Exception::class)
    fun getCarThatIsNotStoredInDatabase() {
        val carDb = dataDaoRoom.getCar((Random().nextInt(1000) + 10000).toString())
        //val carNull = CarData(null, null, null)
        assertTrue(carDb != null)
    }

}
