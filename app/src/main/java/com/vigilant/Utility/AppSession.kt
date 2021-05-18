package com.mssinfotech.mycity.Utility

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by abc on 10/06/2015.
 */
class AppSession {
    fun clearSharedPreference(context: Context) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = settings.edit()
        editor.clear()
        editor.commit()
    }

    fun removeValue(context: Context, key: String?) {
        val settings: SharedPreferences
        val editor: SharedPreferences.Editor
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        editor = settings.edit()
        editor.remove(key)
        editor.commit()
    }

    companion object {
        const val PREFS_NAME = "MYCITYAPP"
        fun save(context: Context, key: String?, text: String?) {
            val settings: SharedPreferences
            val editor: SharedPreferences.Editor
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) //1
            editor = settings.edit() //2
            editor.putString(key, text) //3
            editor.commit() //4
        }

        fun getValue(context: Context, key: String?): String? {
            val settings: SharedPreferences
            var text: String? = ""
            settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            text = settings.getString(key, null)
            return text
        }
    }
}