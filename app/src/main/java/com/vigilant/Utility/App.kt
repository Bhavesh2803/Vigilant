package com.vigilant.Utility

import android.content.Context
import androidx.multidex.MultiDexApplication


class App : MultiDexApplication() {

    lateinit var context: Context

    init {
        instance = this
    }

    companion object {

        private var instance: App? = null

        @Synchronized
        @JvmStatic
        fun getInstance(): App {
            if (instance == null) {
                instance = App()
            }
            return instance!!
        }

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        //        socket.connect();
        context = this
    }
}