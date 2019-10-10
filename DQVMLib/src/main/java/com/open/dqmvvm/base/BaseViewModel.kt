package com.open.dqmvvm.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel(){
    val loading = MutableLiveData<Boolean>()

    init {
        loading.value = false
    }
}