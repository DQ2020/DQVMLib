package com.open.dqmvvm.util

import android.content.Context
import android.content.SharedPreferences
import com.open.dqmvvm.R

object SPUtil {
    lateinit var instance: SharedPreferences

    fun init(context: Context) {
        with(context) {
            instance = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
            L.d("init SP:::$instance")
        }
    }

    fun getString(key: String): String {
        return instance.getString(key, "")!!
    }

    fun setValue(key: String,value:Any) {
        L.d("$key:::$value")
       with(instance.edit()){
           when(value){
               is String -> putString(key,value)
               is Int -> putInt(key,value)
               is Boolean -> putBoolean(key,value)
               is Float -> putFloat(key,value)
               is Long-> putLong(key,value)
               else -> L.e("2020 SPUtil::unKnown data!")
           }
           apply()
       }
    }
}