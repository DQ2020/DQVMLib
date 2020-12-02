package com.open.dqmvvm.net

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Net {

    val net:INet by lazy {
        Retrofit.Builder()
                .baseUrl("https://getman.cn/mock/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient())
                .build()
                .create(INet::class.java)
    }

    fun init(): INet {
        return net
    }
}