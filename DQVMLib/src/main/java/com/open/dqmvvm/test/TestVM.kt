package com.open.dqmvvm.test

import androidx.lifecycle.MutableLiveData
import com.open.dqmvvm.base.BaseViewModel

class TestVM :BaseViewModel(){
    val label by lazy {
        MutableLiveData("hello from viewModel")
    }
}