package com.open.dqmvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loading by lazy {
        MutableLiveData(false)
    }

    val next by lazy {
        MutableLiveData<Map<String, Any>>()
    }

    fun startActivity(map: Map<String, Any>?) {
        next.postValue(map)
    }
}