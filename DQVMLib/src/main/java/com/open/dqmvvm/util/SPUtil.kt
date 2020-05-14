package com.open.dqmvvm.util

import android.content.Context
import android.content.SharedPreferences
import com.open.dqmvvm.R
import com.open.dqmvvm.log.L

object SPUtil {
    private var instance: SharedPreferences? = null

    fun init(context: Context) {
        with(context) {
            instance = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
            L.d("SPUtil init:$instance")
        }
    }

    fun getString(key: String): String {
        if (null == instance) throw Exception("please use method @{SPUtil.init(context)} first!")
        return instance!!.getString(key, "")!!
    }

    fun setValue(key: String, value: Any) {
        if (null == instance) throw Exception("please use method @{SPUtil.init(context)} first!")
        L.d("$key:::$value")
        with(instance!!.edit()) {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                else -> L.e("SPUtil::unKnown data type!")
            }
            apply()
        }
    }
}