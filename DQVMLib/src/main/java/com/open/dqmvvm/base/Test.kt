package com.open.dqmvvm.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import android.util.Log

class Test : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        Log.d("2020","onCreate")
    }

    override fun onStart(owner: LifecycleOwner) {
        Log.d("2020","onStart")
    }

    override fun onPause(owner: LifecycleOwner) {
        Log.d("2020","onPause")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        Log.d("2020","onDestroy")
    }
}