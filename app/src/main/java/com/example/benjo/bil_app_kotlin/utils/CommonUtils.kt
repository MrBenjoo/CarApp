package com.example.benjo.bil_app_kotlin.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.example.benjo.bil_app_kotlin.data.room.CarData
import java.io.IOException


class CommonUtils {

    @Throws(IOException::class)
    fun loadJSONFromAsset(context: Context, jsonFileName: String): String {

        val manager = context.assets
        val `is` = manager.open(jsonFileName)

        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, charset("UTF-8"))
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun listToArrayList(list: List<CarData>): ArrayList<CarData> {
        val arrayList = arrayListOf<CarData>()
        for (item in list) {
            arrayList.add(CarData(null, item.reg, item.model, item.modelYear, item.type, item.vin, item.json))
        }
        return arrayList
    }

}