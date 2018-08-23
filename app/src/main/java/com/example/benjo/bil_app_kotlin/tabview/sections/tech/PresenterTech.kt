package com.example.benjo.bil_app_kotlin.tabview.sections.tech

import android.util.Log
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.network.json_parsing.JsonHandler
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.example.benjo.bil_app_kotlin.tabview.sections.SectionsContract
import com.google.gson.GsonBuilder
import retrofit2.Response

class PresenterTech(val view: SectionsContract.ViewTech) : SectionsContract.Presenter {
    private var jsonTechText: Result? = null


    override fun getJson(): Result? = jsonTechText

    /*
        Denna metoden anropas varje gång man söker från tabview.
         */
    override fun updateTab(response: Response<Result>?) {
        view.showProgess()
        if (response != null) {
            if (response.isSuccessful) {
                val jsonTech = response.body()?.carInfo?.technical?.data

                with(GsonBuilder().create()) {
                    val mapTech = fromJson(toJson(jsonTech), HashMap<String, String?>()::class.java)
                    updateTab(mapTech)
                }
            } else
                view.showErrorHTTP()
        }
        view.hideProgress()
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