package com.example.benjo.bil_app_kotlin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.util.Log
import com.example.benjo.bil_app_kotlin.network.retrofit.Lambda
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_1
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_2
import com.example.benjo.bil_app_kotlin.Constants.Companion.TITLE_TAB_3
import com.example.benjo.bil_app_kotlin.list.expandable.TestFragment
import com.example.benjo.bil_app_kotlin.list.Row
import com.example.benjo.bil_app_kotlin.network.*
import com.example.benjo.bil_app_kotlin.network.json_parsing.BasicInfo
import com.example.benjo.bil_app_kotlin.network.json_parsing.JsonMapping
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.example.benjo.bil_app_kotlin.network.json_parsing.TechnicalData
import com.example.benjo.bil_app_kotlin.network.retrofit.SearchRegProvider

import retrofit2.Response

import com.google.gson.GsonBuilder


class Presenter(private val activity: MainActivity, map: HashMap<String, Fragment>) : BroadcastReceiver() {
    private val TAG = "Presenter"
    /*var fragmentTab1: BaseFragment? = null
    var fragmentTab2: BaseFragment? = null
    var fragmentTab3: BaseFragment? = null*/

    var fragmentTab1: BaseFragment? = null
    var fragmentTab2: TestFragment? = null
    var fragmentTab3: BaseFragment? = null

    var connected = false
    var startTime: Long = 0


    init {
        fragmentTab1 = map[TITLE_TAB_1] as BaseFragment
        fragmentTab2 = map[TITLE_TAB_2] as TestFragment
        fragmentTab3 = map[TITLE_TAB_3] as BaseFragment
        activity.registerReceiver(this, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    fun search(reg: String?) = when (connected) {
        true -> {
            startTime = System.nanoTime()
            SearchRegProvider
                    .provideSearchReg()
                    .searchReg(reg)
                    .enqueue(Lambda().callback { throwable, response ->
                        throwable.let { onFailure(throwable) }
                        response.let { onResponse(response) }
                    })
        }
        false -> activity.showText("Ingen internetanslutning")
    }

    private fun onFailure(throwable: Throwable?) {
        if (throwable != null)
            activity.showText("Kunde inte läsa in data från server")
    }

    private fun onResponse(response: Response<Result>?) = when (response != null) {
        true -> {
            with(response!!) {
                if (isSuccessful)
                    processResponse(body())
                else
                    activity.showText("Klientfel eller serverfel")
            }
        }
        false -> activity.showText("Response == NULL")
    }

    private fun processResponse(result: Result?) {
        with(GsonBuilder().create()) {
            val toJsonBasic = toJson(result?.carInfo?.basic?.data)
            val toJsonTech = toJson(result?.carInfo?.technical?.data)

            val mapBasic = fromJson(toJsonBasic, HashMap<String, String>()::class.java)
            val mapTechnical = fromJson(toJsonTech, HashMap<String, String>()::class.java)

            updateTab(1, mapBasic)
            updateTab(2, mapTechnical)
        }
        Log.d(TAG, "\n******************** Code execution time: " + ((System.nanoTime() - startTime) / 1E6).toString())
    }


    private fun updateTab(tab: Int, map: Map<String, String>?) {
        if (map != null) {
            /*val list = ArrayList<Row>()
            for (key in map.keys) {
                if (key.isNotEmpty() && !map[key].isNullOrEmpty()) {
                    when (tab) {
                        1 -> list.add(JsonMapping(activity).basicInfoMapping(key, map[key]))
                        2 -> list.add(JsonMapping(activity).techInfoMapping(key, map[key]))
                    }
                }
            }*/



            when (tab) {
                1 -> {
                    val list = ArrayList<Row>()
                    for (key in map.keys) {
                        if (key.isNotEmpty() && !map[key].isNullOrEmpty()) {
                            list.add(JsonMapping(activity).basicInfoMapping(key, map[key]))
                        }
                    }
                    fragmentTab1?.updateList(list)
                }
                2 -> {
                    fragmentTab2?.updateList("Dimensioner", dimensioner(map))
                    fragmentTab2?.updateList("Teknisk data", teknisk(map))
                }//fragmentTab2?.updateList(list)
            }
        }
    }

    private fun teknisk(map: Map<String, String>): ArrayList<Row> {
        val list = arrayListOf<Row>()
        with(JsonMapping(activity)) {
            with(Constants) {
                with(list) {
                    if (!map[HP].isNullOrEmpty()) add(techInfoMapping(HP, map[HP]))
                    if (!map[KW].isNullOrEmpty()) add(techInfoMapping(KW, map[KW]))
                    if (!map[TOP_SPEED].isNullOrEmpty()) add(techInfoMapping(TOP_SPEED, map[TOP_SPEED]))
                    if (!map[FUEL].isNullOrEmpty()) add(techInfoMapping(FUEL, map[FUEL]))
                    if (!map[CONSUMPTION].isNullOrEmpty()) add(techInfoMapping(CONSUMPTION, map[CONSUMPTION]))
                    if (!map[CO2].isNullOrEmpty()) add(techInfoMapping(CO2, map[CO2]))
                    if (!map[TRANSMISSION].isNullOrEmpty()) add(techInfoMapping(TRANSMISSION, map[TRANSMISSION]))
                    if (!map[FOUR_WHEEL_DRIVE].isNullOrEmpty()) add(techInfoMapping(FOUR_WHEEL_DRIVE, map[FOUR_WHEEL_DRIVE]))
                    if (!map[SOUND_LVL].isNullOrEmpty()) add(techInfoMapping(SOUND_LVL, map[SOUND_LVL]))
                    if (!map[PASSENGER_AB].isNullOrEmpty()) add(techInfoMapping(PASSENGER_AB, map[PASSENGER_AB]))
                    if (!map[HITCH].isNullOrEmpty()) add(techInfoMapping(HITCH, map[HITCH]))
                }
            }
        }
        return list
    }


    private fun dimensioner(map: Map<String, String>): ArrayList<Row> {
        val list = arrayListOf<Row>()
        with(JsonMapping(activity)) {
            with(Constants) {
                with(list) {
                    add(techInfoMapping(LENGTH, map[LENGTH]))
                    add(techInfoMapping(WIDTH, map[WIDTH]))
                    add(techInfoMapping(HEIGHT, map[HEIGHT]))
                    add(techInfoMapping(CARR_WEIGHT, map[CARR_WEIGHT]))
                    add(techInfoMapping(TOTAL_WEIGHT, map[TOTAL_WEIGHT]))
                    add(techInfoMapping(TRAILER_WEIGHT, map[LOAD_WEIGHT]))
                    add(techInfoMapping(UNBR_TRAILER_WEIGHT, map[LOAD_WEIGHT]))
                    add(techInfoMapping(TRAILER_WEIGHT_B, map[LOAD_WEIGHT]))
                    add(techInfoMapping(TRAILER_WEIGHT_BE, map[LOAD_WEIGHT]))
                    add(techInfoMapping(TIRE_FRONT, map[TIRE_FRONT]))
                    add(techInfoMapping(TIRE_BACK, map[TIRE_BACK]))
                    add(techInfoMapping(RIM_FRONT, map[RIM_FRONT]))
                    add(techInfoMapping(RIM_BACK, map[RIM_BACK]))
                }
            }
        }

        return list
    }


    private fun row(id: Int, value: String?): Row = Row(desc(id), value)
    private fun desc(id: Int): String = activity.resources.getString(id)


    override fun onReceive(context: Context?, intent: Intent?) = when (intent?.action) {
        ConnectivityManager.CONNECTIVITY_ACTION ->
            with(activity) {
                if (Connectivity(applicationContext).isConnected()) {
                    connected = true
                    internetOn()
                } else {
                    connected = false
                    internetOff()
                }
            }
        else -> Unit
    }


    fun onDestroy() {
        activity.unregisterReceiver(this)
    }

    private fun testCode() {
        val basic = BasicInfo(null, "S80", "I trafik", "Svart", "Personbil", "2003", "2003")
        val tehnical = TechnicalData("180", "230", "150", "300", "Bensin", "5",
                "20", "Ja", "4", "ja", "ja", "2", "fan vet jag",
                "1500", "4500", "3000", "1500", "2000", "3000", "4000",
                "5100", "4000", "6000", "5410", "asd", "Xls",
                "das5", "sd5", "640", "personbil", "ed", "bd", "fef", "miljö",
                "asd", "asd", "emi_class", "euro_ncap")

        // Jackson
        /*val mapper = ObjectMapper()

        val jsonBasic = mapper.writeValueAsString(basic)
        val jsonTech = mapper.writeValueAsString(tehnical)

        val mapBasic = mapper.readValue<Map<String, String>>(jsonBasic, object : TypeReference<Map<String, String>>() {})
        val mapTechnical = mapper.readValue<Map<String, String>>(jsonTech, object : TypeReference<Map<String, String>>() {})

        updateTab(1, mapBasic)
        updateTab(2, mapTechnical)*/

        // Gson

        val gson = GsonBuilder().create()

        val json = gson.toJson(basic)
        val mapFirst = HashMap<String, String>()

        val map = gson.fromJson(json, mapFirst::class.java)

        if (map != null) {
            val list = ArrayList<Row>()
            for (key in map.keys) {
                if (map[key] != null) {
                    list.add(Row(key, map[key]))
                    Log.d(TAG, "desc = " + key + " data = " + map[key])
                }
            }
        }
    }


}