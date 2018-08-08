package com.example.benjo.bil_app_kotlin.home

import android.content.Intent
import com.example.benjo.bil_app_kotlin.MainActivity
import com.example.benjo.bil_app_kotlin.MainPresenter
import com.example.benjo.bil_app_kotlin.base.Contract
import com.example.benjo.bil_app_kotlin.network.json_parsing.BasicInfo
import com.example.benjo.bil_app_kotlin.network.json_parsing.Result
import com.example.benjo.bil_app_kotlin.network.json_parsing.TechnicalData
import com.google.gson.Gson
import com.google.gson.GsonBuilder


class HomePresenter(val view: Contract.ViewHome, val activity: HomeActivity): Contract.Presenter {

    override fun update(json: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    init {
        view.presenter = this
    }

    override fun search(reg: String?) {
       // view.showProgess()
        /*val response = MainPresenter(view.getContext()).search(reg)
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
        val basic = BasicInfo("Volvo S80", "S80", "I trafik", "Svart", "Personbil", "2003", "2003")
        val tehnical = TechnicalData("180", "230", "1798 ", "200 ", "Bensin / Etanol", "7.4",
                "177", "manuell ", "ja", "71", "4", "ja", "fan vet jag",
                "1500", "Herrgårdsvagn", "3000", "1500", "2000", "3000", "4000",
                "5100", "4000", "6000", "1610", "2360", "1300",
                "205/50 R17 93W", "205/50 R17 93W", "7Jx17x52,5", "7Jx17x52,5", "axel_width", "M1", null/*"e4*2001/116*0076*13"*/, "nox_1",
                null, "2005", "emission_class", null)

        val gson = GsonBuilder().create()

        val intent = Intent(/*view.getContext()*/activity, MainActivity::class.java)
        //val basicData = body?.carInfo?.basic?.data
        //val techData = body?.carInfo?.technical?.data
        val jsonBasic = gson.toJson(basic)
        val jsonTech = gson.toJson(tehnical)
        //val jsonTech = Gson().toJson(techData)
        intent.putExtra("basic", jsonBasic)
        intent.putExtra("technical", jsonTech)
        //intent.putExtra("tech", jsonTech)
        /*view.getContext()*/activity.startActivity(intent)
    }


}