package com.open.dqmvvm.annotation


class Data(private var str: String?, num: Int) {

    private var num: Int? = num

    override fun toString(): String {
        return "Data`s data is ".plus(str).plus(num)
    }
}
