package com.example.benjo.bil_app_kotlin.home

import android.content.Intent
import android.util.Log
import com.example.benjo.bil_app_kotlin.network.json.*
import com.example.benjo.bil_app_kotlin.tabs.TabsActivity
import com.google.gson.GsonBuilder
import java.util.*


class HomePresenter(val view: HomeContract.View,
                    val activity: HomeActivity) : HomeContract.Presenter {

    override fun attachView(v: HomeContract.View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val TAG = "HomePresenter"


    override fun search(reg: String?) {
       // view.showProgess()
        /*val response = TabsPresenter(view.getContext()).search(reg)
        if (response != null) {
            if (response.isSuccessful)
                processResponse(response.body())
            else
                view.showErrorHTTP()
        }*/
        processResponse(null) // test...
        //view.hideProgress()
    }

    private fun processResponse(body: Result?) {
        /* ------------------------------------------------------------- Används endast för att testa UI -------------------------------------------------------------*/
        val mColors = arrayOf("Svart", "Vit", "Blå", "Orange", "Silver")
        val mMake = arrayOf("Volvo S80", "Volvo S70", "Volvo S60", "Volvo S50", "Volvo S40")

        val attrData = Attributes("RON810", (Random().nextInt(20) + 1).toString())
        val basicData = BasicInfo(mMake[Random().nextInt(5)], "S80", "I trafik", mColors[Random().nextInt(5)], "Personbil", "2003", "2003")
        val techData = TechnicalData("180",
                "230",
                "1798 ",
                "200 ",
                "Bensin / Etanol",
                "7.4",
                "177",
                "manuell ",
                "ja",
                "71",
                "4",
                "ja",
                "fan vet jag",
                "1500",
                "Herrgårdsvagn",
                "3000",
                "1500",
                "2000",
                "3000",
                "4000",
                "5100",
                "4000",
                "6000",
                "1610",
                "2360",
                "1300",
                "205/50 R17 93W",
                "205/50 R17 93W",
                "7Jx17x52,5",
                "7Jx17x52,5",
                "axel_width",
                "M1",
                null/*"e4*2001/116*0076*13"*/,
                "nox_1",
                null,
                "2005",
                "emission_class",
                null)
        val result = Result(CarInfo(attrData, Basic(basicData), Technical(techData)))
        /* --------------------------------------------------------------------------------------------------------------------------------------------------------- */

        val gson = GsonBuilder().create()

        val intent = Intent(activity, TabsActivity::class.java)
        val jsonResult = gson.toJson(result).toString()
        Log.d(TAG, "processResponse ->  OBJECT to JSON = $jsonResult")

        val classResult = gson.fromJson(jsonResult, Result::class.java)
        Log.d(TAG, "processResponse ->  JSON to OBJECT = ${classResult?.carInfo?.attributes?.regno}")

        intent.putExtra("jsonResult", jsonResult)

        activity.startActivity(intent)
    }

}