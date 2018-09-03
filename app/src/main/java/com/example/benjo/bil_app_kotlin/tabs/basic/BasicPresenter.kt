package com.example.benjo.bil_app_kotlin.tabs.basic

import android.util.Log
import com.example.benjo.bil_app_kotlin.network.json.JsonHandler
import com.example.benjo.bil_app_kotlin.network.json.Result
import com.example.benjo.bil_app_kotlin.tabs.TabsContract
import com.google.gson.GsonBuilder
import retrofit2.Response

class BasicPresenter : TabsContract.BasicPresenter {
    private val TAG = "BasicPresenter"
    private lateinit var view: TabsContract.ViewBasic


    override fun attachView(v: TabsContract.ViewBasic) {
        this.view = v
    }


    /*
    Denna metoden anropas varje gång man söker från tabview.
     */
    override fun updateTab(response: Response<Result>?) {

        if (response != null) {
            if (response.isSuccessful) {
                val jsonBasic = response.body()?.carInfo?.basic?.data
                with(GsonBuilder().create()) {
                    val mapBasic = fromJson(toJson(jsonBasic), HashMap<String, String?>()::class.java)
                    val list = JsonHandler(view.getContext()).basicSection(mapBasic)
                    if (!list.isEmpty()) view.updateList(list)
                }
            } else
                Log.d(TAG, "error")
        }
    }


    /*
    Denna metoden anropas när onResume körs. Det vill säga när man startar TabsActivity från
    HomeActivity.
     */
    override fun update(jsonResult: String?) {
        val gson = GsonBuilder().create()
        var objResult = gson.fromJson(jsonResult, Result::class.java)
        objResult.let {
            val jsonBasicText = gson.toJson(it?.carInfo?.basic?.data)
            val mapBasic = gson.fromJson(jsonBasicText, HashMap<String, String?>()::class.java)
            val list = JsonHandler(view.getContext()).basicSection(mapBasic)
            if (!list.isEmpty()) {
                Log.d(TAG, "update -> view will be updated (list not null)")
                view.updateList(list)
            } else {
                Log.d(TAG, "update -> view will not be updated (list is null)")
            }
        }
    }


}