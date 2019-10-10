package com.open.dqmvvm.login

import android.view.View
import androidx.databinding.BindingAdapter

object LoginAdapter{

    @JvmStatic
    @BindingAdapter(value = ["vm"])
    fun rememberCheckChanged(v: View,vm:LoginVM){
        vm.rememberAccountAndPassword()
    }
}