package com.example.benjo.bil_app_kotlin.tabs.tech

import android.util.Log
import com.example.benjo.bil_app_kotlin.network.json.JsonHandler
import com.example.benjo.bil_app_kotlin.network.json.Result
import com.example.benjo.bil_app_kotlin.tabs.TabsContract
import com.google.gson.GsonBuilder
import retrofit2.Response

class TechPresenter : TabsContract.TechPresenter {
    private val TAG = "TechPresenter"
    private lateinit var view: TabsContract.ViewTech


    override fun attachView(v: TabsContract.ViewTech) {
        this.view = v
    }


    /*
        Denna metoden anropas varje gång man söker från tabview.
    */
    override fun updateTab(response: Response<Result>?) {
        if (response != null) {
            if (response.isSuccessful) {
                val jsonTech = response.body()?.carInfo?.technical?.data

                with(GsonBuilder().create()) {
                    val mapTech = fromJson(toJson(jsonTech), HashMap<String, String?>()::class.java)
                    updateTab(mapTech)
                }
            } else
                Log.d(TAG, "error")
        }
    }


    private fun updateTab(map: HashMap<String, String?>?) {
        with(JsonHandler(view.getContext())) {
            val techSection = techSection(map)
            val dimensionsSection = dimensionsSection(map)
            val otherSection = otherSection(map)
            if (!techSection.isEmpty()) view.updateList("Teknisk data", techSection)
            if (!dimensionsSection.isEmpty()) view.updateList("Dimensioner", dimensionsSection)
            if (!otherSection.isEmpty()) view.updateList("Övrigt", otherSection)
        }
    }


    /*
    Denna metoden anropas när onResume körs. Det vill säga när man startar TabsActivity från
    HomeActivity.
     */
    override fun update(jsonResult: String?) {
        with(GsonBuilder().create()) {
            val objResult = fromJson(jsonResult, Result::class.java)
            val jsonTechText = toJson(objResult.carInfo?.technical?.data)
            val mapTech = fromJson(jsonTechText, HashMap<String, String?>()::class.java)
            updateTab(mapTech)
        }
    }

}