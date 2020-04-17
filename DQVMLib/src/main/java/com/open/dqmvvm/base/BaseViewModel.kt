package com.open.dqmvvm.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {

    val loading by lazy {
        MutableLiveData(false)
    }

    val next by lazy {
        MutableLiveData<Bundle>()
    }

    fun startActivity() {
        startActivity(null)
    }

    fun startActivity(bundle: Bundle?) {
        next.postValue(bundle)
    }
}