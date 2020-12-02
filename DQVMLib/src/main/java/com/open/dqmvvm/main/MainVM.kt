package com.open.dqmvvm.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.open.dqmvvm.base.BaseViewModel
import com.open.dqmvvm.mvvm.MvvmActivity

class MainVM :BaseViewModel(){

    fun start(v:View) {
        val map = HashMap<String,Any>()
        map["class"] = MvvmActivity::class.java
        map["param1"] = "this is first param"
        startActivity(map)
    }
}