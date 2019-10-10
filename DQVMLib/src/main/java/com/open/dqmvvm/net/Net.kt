package com.open.dqmvvm.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit

object Net {

    val net:INet by lazy {
        Retrofit.Builder()
                .baseUrl("https://www.baidu.com")
                .client(OkHttpClient())
                .build()
                .create(INet::class.java)
    }

    fun init(): INet {
        return net
    }
}