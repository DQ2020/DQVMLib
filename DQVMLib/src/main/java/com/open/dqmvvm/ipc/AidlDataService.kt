package com.open.dqmvvm.ipc

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.open.dqmvvm.IMyAidlInterface

class AidlDataService : Service() {

    override fun onBind(intent: Intent): IBinder {
       return object : IMyAidlInterface.Stub(){
           override fun getAidlData(): AidlData {
               return AidlData("gu", 100)
           }
       }
    }
}
