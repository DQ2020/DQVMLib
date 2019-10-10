package com.open.dqmvvm.util

import android.util.Log

object L {
    const val TAG = "2020"
    @JvmStatic
    fun e(msg: String) {
        Log.e(TAG, msg)
    }

    @JvmStatic
    fun d(msg: String) {
        Log.d(TAG, msg)
    }
}