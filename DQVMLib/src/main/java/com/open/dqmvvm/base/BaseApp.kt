package com.open.dqmvvm.base

import android.app.Application
import android.content.Context
import com.open.dqmvvm.log.L
import com.open.dqmvvm.util.DBUtil
import com.open.dqmvvm.util.SPUtil

class BaseApp : Application() {

    init {
        L.d("app start!")
        L.d("Application = $this")
        //L.d("applicationContext = ${applicationContext==null}")  error applicationContext is null
    }

    override fun onCreate() {
        super.onCreate()
        L.d("app onCreate!")
        SPUtil.init(this)
        DBUtil.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        L.d("app attachBaseContext!")
    }
}