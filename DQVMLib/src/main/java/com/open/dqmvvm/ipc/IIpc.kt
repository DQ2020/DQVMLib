package com.open.dqmvvm.ipc

import android.os.IInterface

interface IIpc : IInterface {
    fun getDataFromAnotherProcess(): Data

    fun getString(num:Int): String
}