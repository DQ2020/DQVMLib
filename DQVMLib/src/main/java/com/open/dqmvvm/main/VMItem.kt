package com.open.dqmvvm.main

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider

class VMItem(var name:String, var clazz:Class<*>){

    val nameO:ObservableField<String> = ObservableField()
    val clazzO:MutableLiveData<String> = MutableLiveData()

    init {
        nameO.set(name)
        clazzO.value = clazz.name
    }

    fun click(v: View){
        nameO.set(nameO.get().plus("++"))
        clazzO.value =  "clazzO has ok"
        ViewModelProvider(v.context as MainActivity)[MainVM::class.java].apply {
            val map = HashMap<String,Any>()
            map["class"] = clazz
            startActivity(map)
        }
    }
}