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
import com.example.benjo.bil_app_kotlin.list.expandable.ExpandableFragment
import com.example.benjo.bil_app_kotlin.list.model.Row
import com.example.benjo.bil_app_kotlin.list.ordinary.BasicListFragment
import com.example.benjo.bil_app_kotlin.network.*
import com.example.benjo.bil_app_kotlin.network.json_parsing.*
import com.example.benjo.bil_app_kotlin.network.retrofit.SearchRegProvider

import retrofit2.Response

import com.google.gson.GsonBuilder


class Presenter(private val activity: MainActivity,
                map: HashMap<String, Fragment>) : BroadcastReceiver() {


    /* ----------------- Fragments ----------------- */
    private var fragmentTab1: BasicListFragment? = null
    private var fragmentTab2: ExpandableFragment? = null
    private var fragmentTab3: BasicListFragment? = null
    /* --------------------------------------------- */

    /* --- Miscellaneous stuff --- */
    private var connected = false
    private var startTime: Long = 0
    private val TAG = "Presenter"
    /* --------------------------- */



    init {
        fragmentTab1 = map[TITLE_TAB_1] as BasicListFragment
        fragmentTab2 = map[TITLE_TAB_2] as ExpandableFragment
        fragmentTab3 = map[TITLE_TAB_3] as BasicListFragment
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
                        response.let {
                            testCode()
                            //onResponse(response)
                        }
                    })
        }
        false -> activity.showText(activity.resources.getString(R.string.error_no_internet))
    }

    private fun onFailure(throwable: Throwable?) {
        if (throwable != null)
            activity.showText(activity.resources.getString(R.string.error_api_failure))
    }

    private fun onResponse(response: Response<Result>?) = when (response != null) {
        true -> {
            with(response!!) {
                if (isSuccessful)
                    processResponse(body())
                else
                    activity.showText(activity.resources.getString(R.string.error_http_code))
            }
        }
        false -> Unit
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
            val handler = JsonHandler(activity)
            when (tab) {
                1 -> fragmentTab1?.updateList(handler.basicSection(map))
                2 -> {
                    fragmentTab2?.updateList("Teknisk data", handler.techSection(map))
                    fragmentTab2?.updateList("Dimensioner", handler.dimensionsSection(map))
                    fragmentTab2?.updateList("Övrigt", handler.otherSection(map))
                }
            }
        }
    }


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
        val basic = BasicInfo("Volvo S80", "S80", "I trafik", "Svart", "Personbil", "2003", "2003")
        val tehnical = TechnicalData("180", "230", "1798 ", "200 ", "Bensin / Etanol", "7.4",
                "177", "manuell ", "ja", "71", "4", "ja", "fan vet jag",
                "1500", "Herrgårdsvagn", "3000", "1500", "2000", "3000", "4000",
                "5100", "4000", "6000", "1610", "2360", "1300",
                "205/50 R17 93W", "205/50 R17 93W", "7Jx17x52,5", "7Jx17x52,5", "axel_width", "M1", null/*"e4*2001/116*0076*13"*/, "nox_1",
                null, "2005", "emission_class", null)

        // Gson

        val gson = GsonBuilder().create()

        val jsonBasic = gson.toJson(basic)
        val jsonTech = gson.toJson(tehnical)
        val map1 = HashMap<String, String>()
        val map2 = HashMap<String, String>()

        val mapBasic = gson.fromJson(jsonBasic, map1::class.java)
        val mapTech = gson.fromJson(jsonTech, map2::class.java)

        updateTab(1, mapBasic)
        updateTab(2, mapTech)

    }

}