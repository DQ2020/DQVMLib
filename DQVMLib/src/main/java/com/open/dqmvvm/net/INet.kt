package com.open.dqmvvm.net

import com.open.dqmvvm.login.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface INet {

    @GET("login")
    suspend fun login(): User
}