package com.example.benjo.swecar

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.example.benjo.swecar.ui.home.LocaleManager
import com.squareup.leakcanary.LeakCanary
import java.lang.ref.WeakReference


class App : Application() {


    /* If WeakReference was not used, the garbage collector wouldn't be able to free the memory of this
    "App" class instance because of the static context. */
    companion object {
        lateinit var mContext: WeakReference<Context>
        lateinit var localeManager: LocaleManager

        fun getContext(): Context {
            return mContext.get()!!
        }

        fun isConnected(): Boolean {
            val cm = getContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            return activeNetwork?.isConnected == true
        }

    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this);
        mContext = WeakReference(this)
    }

    override fun attachBaseContext(base: Context?) {
        Log.d("LifeCycles", "App -> attachBaseContext")
        localeManager = LocaleManager(base!!)
        super.attachBaseContext(localeManager.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        localeManager.setLocale(this)
    }


}