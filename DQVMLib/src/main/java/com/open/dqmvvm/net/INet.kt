package com.open.dqmvvm.net

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface INet {

    @GET("/")
    fun baidu(): Call<ResponseBody>
}