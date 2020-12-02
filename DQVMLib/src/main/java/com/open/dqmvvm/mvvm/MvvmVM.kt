package com.open.dqmvvm.mvvm

import androidx.lifecycle.MutableLiveData
import com.open.dqmvvm.base.BaseViewModel

class MvvmVM : BaseViewModel(){

    val text by lazy {
        MutableLiveData<String>()
    }
}