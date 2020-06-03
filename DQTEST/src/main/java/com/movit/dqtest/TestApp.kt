package com.movit.dqtest

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

class TestApp : Application(){

    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.openDebug()
        ARouter.init(this)
    }
}