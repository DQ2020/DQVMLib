package com.open.dqmvvm.util

interface ILoading{
    val loading:Loading?

    fun show(){
        loading?.show()
    }

    fun dismiss(){
        loading?.dismiss()
    }
}