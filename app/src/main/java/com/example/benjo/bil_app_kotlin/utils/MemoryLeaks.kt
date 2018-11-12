package com.example.benjo.bil_app_kotlin.utils

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import java.lang.ref.WeakReference

class MemoryLeaks: Application() {

    /* if WeakReference was not used, the garbage collector cannot free the memory of this
    "MemoryLeaks" class instance because of the static context. */
    companion object {
        lateinit var mContext : WeakReference<Context>
        fun getContext() : Context {
            return mContext.get()!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        // Normal app init code...
        mContext = WeakReference(this)
    }



}