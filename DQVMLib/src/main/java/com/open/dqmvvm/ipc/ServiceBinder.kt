package com.open.dqmvvm.ipc

import android.os.Binder
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import com.open.dqmvvm.log.L

class ServiceBinder : Binder(), IIpc {

    companion object {
        fun asInterface(binder: IBinder?): IIpc {
            val i = binder?.queryLocalInterface(DESCRIPTOR)
            return if (i is IIpc)
                i
            else Proxy(binder!!)
        }

        const val DESCRIPTOR = "com.open.dqmvvm.ipc.IIpc"
    }


    init {
        attachInterface(this, DESCRIPTOR)
    }

    override fun getDataFromAnotherProcess(): Data {
        return Data("server data", 100)
    }

    override fun getString(num: Int): String {
        return "num is : ".plus(num)
    }

    override fun asBinder(): IBinder {
        return this
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        L.d("===onTransact===")
        when (code) {
            IBinder.INTERFACE_TRANSACTION -> reply?.writeString(DESCRIPTOR)
            IBinder.FIRST_CALL_TRANSACTION -> {
                data.enforceInterface(DESCRIPTOR)
                reply?.writeNoException()
                val back = getDataFromAnotherProcess()
                L.d("back $back")
                L.d("===write===")
                reply?.writeParcelable(back, 0)
                L.d("===write  end===")
                reply?.writeString("aaa")
                reply?.recycle()
                data.recycle()
            }
            IBinder.FIRST_CALL_TRANSACTION + 1 -> {
                reply?.writeNoException()
                val num = data.readInt()
                L.d("service rec:$num")
                reply?.writeString(getString(num))
                reply?.recycle()
                data.recycle()
            }
        }
        return true
    }
}