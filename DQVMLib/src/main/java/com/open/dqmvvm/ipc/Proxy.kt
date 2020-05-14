package com.open.dqmvvm.ipc

import android.os.IBinder
import android.os.Parcel
import com.open.dqmvvm.log.L

class Proxy(private val iBinder: IBinder) : IIpc {

    override fun getDataFromAnotherProcess(): Data {
        L.d("===Proxy===")
        val data = Parcel.obtain()
        val replay = Parcel.obtain()
        data.writeInterfaceToken(ServiceBinder.DESCRIPTOR)
        iBinder.transact(IBinder.FIRST_CALL_TRANSACTION, data, replay, 0)
        replay.readException()
        L.d("===read===")
        val get = replay.readParcelable<Data>(Data::class.java.classLoader)
        L.d("===read  end===")
        L.d("get $get ${hashCode()}")
        L.d("test  ${replay.readString()}")
        replay.recycle()
        data.recycle()
        return get!!
    }

    override fun getString(num: Int): String {
        val data = Parcel.obtain()
        val replay = Parcel.obtain()
        data.writeInt(num)
        iBinder.transact(IBinder.FIRST_CALL_TRANSACTION + 1, data, replay, 0)
        replay.readException()
        val res = replay.readString()!!
        replay.recycle()
        data.recycle()
        return res
    }

    override fun asBinder(): IBinder? {
        return iBinder
    }
}