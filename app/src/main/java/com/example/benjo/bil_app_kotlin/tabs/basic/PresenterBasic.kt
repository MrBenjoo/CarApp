package com.example.benjo.bil_app_kotlin.tabs.basic

import android.util.Log
import com.example.benjo.bil_app_kotlin.network.json.JsonHandler
import com.example.benjo.bil_app_kotlin.network.json.Result
import com.example.benjo.bil_app_kotlin.tabs.SectionsContract
import com.google.gson.GsonBuilder
import retrofit2.Response

class PresenterBasic(val view: SectionsContract.ViewBasic) : SectionsContract.BasicPresenter {


    override fun attachView(v: SectionsContract.ViewBasic) {

    }

    private val TAG = "PresenterBasic"
    private var objResult: Result? = null


    /*
    Denna metoden anropas varje gång man söker från tabview.
     */
    override fun updateTab(response: Response<Result>?) {

        if (response != null) {
            if (response.isSuccessful) {
                val jsonBasic = response.body()?.carInfo?.basic?.data
                objResult = response.body()
                with(GsonBuilder().create()) {
                    val mapBasic = fromJson(toJson(jsonBasic), HashMap<String, String?>()::class.java)
                    val list = JsonHandler(view.getContext()).basicSection(mapBasic)
                    if (!list.isEmpty()) view.updateList(list)
                }
            } else
               Log.d(TAG, "error")
        }
    }

    override fun getJson(): Result? = objResult


    /*
    Denna metoden anropas när onResume körs. Det vill säga när man startar TabsActivity från
    HomeActivity.
     */
    override fun update(jsonResult: String?) {
        val gson = GsonBuilder().create()
        objResult = gson.fromJson(jsonResult, Result::class.java)
        objResult.let {
            val jsonBasicText = gson.toJson(it?.carInfo?.basic?.data)
            val mapBasic = gson.fromJson(jsonBasicText, HashMap<String, String?>()::class.java)
            val list = JsonHandler(view.getContext()).basicSection(mapBasic)
            if (!list.isEmpty()) {
                Log.d(TAG, "update -> view will be updated (list not null)")
                view.updateList(list)
            } else {
                Log.d(TAG, "update -> view will not be updated (list is null)")
            }}
    }


}