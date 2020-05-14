package com.open.dqmvvm.ipc

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.open.dqmvvm.R
import com.open.dqmvvm.log.L

class ClientIpc : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_ipc)

        val intent = Intent(this, ServerService::class.java)
        bindService(intent, object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                val asInterface = ServiceBinder.asInterface(service)
                val dataFromAnotherProcess = asInterface.getDataFromAnotherProcess()
                L.d(dataFromAnotherProcess.toString())
//                L.d(asInterface.getString(555))
            }

        }, Context.BIND_AUTO_CREATE)
    }
}
