package com.open.dqmvvm.log

import android.util.Log
import com.open.dqmvvm.constant.Constant.TAG

object L{

    fun d(d:String?){
        Log.d(TAG,d?:"null")
    }

    fun e(e:String?){
        Log.e(TAG,e?:"null")
    }
}