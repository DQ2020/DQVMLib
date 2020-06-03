package com.open.dqmvvm.ipc

import android.os.Parcel
import android.os.Parcelable
import com.open.dqmvvm.log.L

class AidlData : Parcelable {

    private var str: String? = null
    private var num: Int? = null

    constructor(parcel: Parcel) {
        L.d("constructor by parcel")
        this.str = parcel.readString()
        this.num = parcel.readInt()
    }

    constructor(str: String?, num: Int) {
        this.str = str
        this.num = num
        L.d("constructor real ${this.str} ${this.num} ${hashCode()}")
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        L.d("writeToParcel  $str")
        L.d("writeToParcel  $num")
        dest.writeString(str)
        dest.writeInt(num!!)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AidlData> {
        override fun createFromParcel(parcel: Parcel): AidlData {
            return AidlData(parcel)
        }

        override fun newArray(size: Int): Array<AidlData?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Data`s data is ".plus(str).plus(num)
    }
}
