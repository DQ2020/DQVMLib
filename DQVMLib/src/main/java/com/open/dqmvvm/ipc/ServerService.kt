package com.open.dqmvvm.ipc

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.open.dqmvvm.log.L

class ServerService : Service() {

    override fun onCreate() {
        super.onCreate()
        L.d("ServerService onCreate")
    }

    private val mStub = ServiceBinder()

    override fun onBind(intent: Intent): IBinder {
        return mStub
    }
}
