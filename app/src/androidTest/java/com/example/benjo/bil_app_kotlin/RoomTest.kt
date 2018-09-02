package com.example.benjo.bil_app_kotlin

import android.support.test.runner.AndroidJUnit4
import org.junit.runner.RunWith
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import com.example.benjo.bil_app_kotlin.room.CarData
import com.example.benjo.bil_app_kotlin.room.CarDataBase
import com.example.benjo.bil_app_kotlin.room.CarDataDao
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var mDb: CarDataBase
    private lateinit var mUserDao: CarDataDao
    private val carData = CarData(1, 10, "{\"data\":{\"type\":\"vehicle\",\"attributes\":{\"regno\":\"RON810\",\"vin\":\"YV1MW084282424649\"},\"links\":[{\"rel\":\"self\",\"uri\":\"vehicle\\/vin\\/YV1MW084282424649\"},{\"rel\":\"biluppgifter\",\"uri\":\"https:\\/\\/biluppgifter.se\\/fordon\\/RON810\"}],\"basic\":{\"data\":{\"make\":\"Volvo\",\"model\":\"V50 1.8 F\",\"status\":2,\"color\":\"Gr\\u00e5\",\"type\":\"PB\",\"vehicle_year\":2008,\"model_year\":2008}},\"technical\":{\"data\":{\"power_hp_1\":125,\"power_hp_2\":null,\"power_hp_3\":null,\"power_kw_1\":92,\"power_kw_2\":null,\"power_kw_3\":null,\"cylinder_volume\":1798,\"top_speed\":200,\"fuel_1\":1,\"fuel_2\":7,\"consumption_1\":7.4,\"consumption_2\":null,\"consumption_3\":null,\"co2_1\":177,\"co2_2\":null,\"co2_3\":null,\"transmission\":1,\"four_wheel_drive\":false,\"sound_level_1\":71,\"sound_level_2\":null,\"sound_level_3\":null,\"number_of_passengers\":4,\"passenger_airbag\":true,\"hitch\":null,\"hitch_2\":null,\"chassi_code_1\":7,\"chassi_code_2\":null,\"chassi\":\"Herrg\\u00e5rdsvagn\",\"color\":\"Gr\\u00e5\",\"length\":4550,\"width\":1770,\"height\":1460,\"kerb_weight\":1380,\"total_weight\":1890,\"load_weight\":510,\"trailer_weight\":1300,\"unbraked_trailer_weight\":650,\"trailer_weight_b\":1610,\"trailer_weight_be\":2360,\"carriage_weight\":null,\"tire_front\":\"205\\/50 R17 93W\",\"tire_back\":\"205\\/50 R17 93W\",\"rim_front\":\"7Jx17x52,5\",\"rim_back\":\"7Jx17x52,5\",\"axel_width\":null,\"category\":\"M1\",\"eeg\":\"e4*2001\\/116*0076*13\",\"nox_1\":0.019,\"nox_2\":null,\"nox_3\":null,\"thc_nox_1\":null,\"thc_nox_2\":null,\"thc_nox_3\":null,\"eco_class\":4,\"emission_class\":null,\"euro_ncap\":null}}}}")

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getTargetContext()
        mDb = Room.inMemoryDatabaseBuilder(context, CarDataBase::class.java!!).build()
        mUserDao = mDb.carDataDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        mDb.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertCarAndCheckIfInserted() {
        mUserDao.insert(carData)
        val car = mUserDao.getCar(10)
        assertThat(car, equalTo(carData))
    }

    @Test
    @Throws(Exception::class)
    fun insertCarThenDeleteCarAndThenCheckIfDeleted() {
        mUserDao.insert(carData)
        mUserDao.deleteCar(10)
        val car = mUserDao.getCar(10)
        assertNull(car)
    }

    @Test
    @Throws(Exception::class)
    fun writeCarsAndReadInList() {
        val carData1 = CarData(1, 10, "{\"data\":{\"type\":\"vehicle\",\"attributes\":{\"regno\":\"RON810\",\"vin\":\"YV1MW084282424649\"},\"links\":[{\"rel\":\"self\",\"uri\":\"vehicle\\/vin\\/YV1MW084282424649\"},{\"rel\":\"biluppgifter\",\"uri\":\"https:\\/\\/biluppgifter.se\\/fordon\\/RON810\"}],\"basic\":{\"data\":{\"make\":\"Volvo\",\"model\":\"V50 1.8 F\",\"status\":2,\"color\":\"Gr\\u00e5\",\"type\":\"PB\",\"vehicle_year\":2008,\"model_year\":2008}},\"technical\":{\"data\":{\"power_hp_1\":125,\"power_hp_2\":null,\"power_hp_3\":null,\"power_kw_1\":92,\"power_kw_2\":null,\"power_kw_3\":null,\"cylinder_volume\":1798,\"top_speed\":200,\"fuel_1\":1,\"fuel_2\":7,\"consumption_1\":7.4,\"consumption_2\":null,\"consumption_3\":null,\"co2_1\":177,\"co2_2\":null,\"co2_3\":null,\"transmission\":1,\"four_wheel_drive\":false,\"sound_level_1\":71,\"sound_level_2\":null,\"sound_level_3\":null,\"number_of_passengers\":4,\"passenger_airbag\":true,\"hitch\":null,\"hitch_2\":null,\"chassi_code_1\":7,\"chassi_code_2\":null,\"chassi\":\"Herrg\\u00e5rdsvagn\",\"color\":\"Gr\\u00e5\",\"length\":4550,\"width\":1770,\"height\":1460,\"kerb_weight\":1380,\"total_weight\":1890,\"load_weight\":510,\"trailer_weight\":1300,\"unbraked_trailer_weight\":650,\"trailer_weight_b\":1610,\"trailer_weight_be\":2360,\"carriage_weight\":null,\"tire_front\":\"205\\/50 R17 93W\",\"tire_back\":\"205\\/50 R17 93W\",\"rim_front\":\"7Jx17x52,5\",\"rim_back\":\"7Jx17x52,5\",\"axel_width\":null,\"category\":\"M1\",\"eeg\":\"e4*2001\\/116*0076*13\",\"nox_1\":0.019,\"nox_2\":null,\"nox_3\":null,\"thc_nox_1\":null,\"thc_nox_2\":null,\"thc_nox_3\":null,\"eco_class\":4,\"emission_class\":null,\"euro_ncap\":null}}}}")
        mUserDao.insert(carData1)
        val carData2 = CarData(2, 20, "{\"data\":{\"type\":\"vehicle\",\"attributes\":{\"regno\":\"RON810\",\"vin\":\"YV1MW084282424649\"},\"links\":[{\"rel\":\"self\",\"uri\":\"vehicle\\/vin\\/YV1MW084282424649\"},{\"rel\":\"biluppgifter\",\"uri\":\"https:\\/\\/biluppgifter.se\\/fordon\\/RON810\"}],\"basic\":{\"data\":{\"make\":\"Volvo\",\"model\":\"V50 1.8 F\",\"status\":2,\"color\":\"Gr\\u00e5\",\"type\":\"PB\",\"vehicle_year\":2008,\"model_year\":2008}},\"technical\":{\"data\":{\"power_hp_1\":125,\"power_hp_2\":null,\"power_hp_3\":null,\"power_kw_1\":92,\"power_kw_2\":null,\"power_kw_3\":null,\"cylinder_volume\":1798,\"top_speed\":200,\"fuel_1\":1,\"fuel_2\":7,\"consumption_1\":7.4,\"consumption_2\":null,\"consumption_3\":null,\"co2_1\":177,\"co2_2\":null,\"co2_3\":null,\"transmission\":1,\"four_wheel_drive\":false,\"sound_level_1\":71,\"sound_level_2\":null,\"sound_level_3\":null,\"number_of_passengers\":4,\"passenger_airbag\":true,\"hitch\":null,\"hitch_2\":null,\"chassi_code_1\":7,\"chassi_code_2\":null,\"chassi\":\"Herrg\\u00e5rdsvagn\",\"color\":\"Gr\\u00e5\",\"length\":4550,\"width\":1770,\"height\":1460,\"kerb_weight\":1380,\"total_weight\":1890,\"load_weight\":510,\"trailer_weight\":1300,\"unbraked_trailer_weight\":650,\"trailer_weight_b\":1610,\"trailer_weight_be\":2360,\"carriage_weight\":null,\"tire_front\":\"205\\/50 R17 93W\",\"tire_back\":\"205\\/50 R17 93W\",\"rim_front\":\"7Jx17x52,5\",\"rim_back\":\"7Jx17x52,5\",\"axel_width\":null,\"category\":\"M1\",\"eeg\":\"e4*2001\\/116*0076*13\",\"nox_1\":0.019,\"nox_2\":null,\"nox_3\":null,\"thc_nox_1\":null,\"thc_nox_2\":null,\"thc_nox_3\":null,\"eco_class\":4,\"emission_class\":null,\"euro_ncap\":null}}}}")
        mUserDao.insert(carData2)
        val list = mUserDao.getAll()
        for (car in list) {
            assertNotNull(car)
        }
    }

    @Test
    @Throws(Exception::class)
    fun writeCarsAndDeleteAll() {
        for (i in 1..10) {
            mUserDao.insert(CarData(i.toLong(), i, "{\"data\":{\"type\":\"vehicle\",\"attributes\":{\"regno\":\"RON810\",\"vin\":\"YV1MW084282424649\"},\"links\":[{\"rel\":\"self\",\"uri\":\"vehicle\\/vin\\/YV1MW084282424649\"},{\"rel\":\"biluppgifter\",\"uri\":\"https:\\/\\/biluppgifter.se\\/fordon\\/RON810\"}],\"basic\":{\"data\":{\"make\":\"Volvo\",\"model\":\"V50 1.8 F\",\"status\":2,\"color\":\"Gr\\u00e5\",\"type\":\"PB\",\"vehicle_year\":2008,\"model_year\":2008}},\"technical\":{\"data\":{\"power_hp_1\":125,\"power_hp_2\":null,\"power_hp_3\":null,\"power_kw_1\":92,\"power_kw_2\":null,\"power_kw_3\":null,\"cylinder_volume\":1798,\"top_speed\":200,\"fuel_1\":1,\"fuel_2\":7,\"consumption_1\":7.4,\"consumption_2\":null,\"consumption_3\":null,\"co2_1\":177,\"co2_2\":null,\"co2_3\":null,\"transmission\":1,\"four_wheel_drive\":false,\"sound_level_1\":71,\"sound_level_2\":null,\"sound_level_3\":null,\"number_of_passengers\":4,\"passenger_airbag\":true,\"hitch\":null,\"hitch_2\":null,\"chassi_code_1\":7,\"chassi_code_2\":null,\"chassi\":\"Herrg\\u00e5rdsvagn\",\"color\":\"Gr\\u00e5\",\"length\":4550,\"width\":1770,\"height\":1460,\"kerb_weight\":1380,\"total_weight\":1890,\"load_weight\":510,\"trailer_weight\":1300,\"unbraked_trailer_weight\":650,\"trailer_weight_b\":1610,\"trailer_weight_be\":2360,\"carriage_weight\":null,\"tire_front\":\"205\\/50 R17 93W\",\"tire_back\":\"205\\/50 R17 93W\",\"rim_front\":\"7Jx17x52,5\",\"rim_back\":\"7Jx17x52,5\",\"axel_width\":null,\"category\":\"M1\",\"eeg\":\"e4*2001\\/116*0076*13\",\"nox_1\":0.019,\"nox_2\":null,\"nox_3\":null,\"thc_nox_1\":null,\"thc_nox_2\":null,\"thc_nox_3\":null,\"eco_class\":4,\"emission_class\":null,\"euro_ncap\":null}}}}"))
        }
        val listBeforeDeletion = mUserDao.getAll()
        assertTrue(listBeforeDeletion.isNotEmpty())
        mUserDao.deleteAll()
        val listAfterDeletion = mUserDao.getAll()
        assertTrue(listAfterDeletion.isEmpty())
    }

}