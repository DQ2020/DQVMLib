package com.open.dqmvvm.login

import androidx.lifecycle.MutableLiveData
import com.open.dqmvvm.base.BaseViewModel
import com.open.dqmvvm.constant.Constant
import com.open.dqmvvm.log.L
import com.open.dqmvvm.util.SPUtil
import org.json.JSONObject

class LoginVM : BaseViewModel() {

    val account by lazy {
        MutableLiveData<String>()
    }
    val password by lazy {
        MutableLiveData<String>()
    }
    val remPwd by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        if (SPUtil.getString(Constant.USER_INFO).isNotEmpty()) {
            val user = JSONObject(SPUtil.getString(Constant.USER_INFO))
            account.postValue(user.optString("account"))
            password.postValue(user.optString("password"))
            remPwd.postValue(user.optBoolean("remPwd"))
            L.d(user.toString())
        }
    }

    fun rememberAccountAndPassword() {
    }

    fun login() {
        val json = JSONObject()
        json.put("account", account.value)
        json.put("password", password.value)
        json.put("remPwd", remPwd.value)
        L.d(json.toString())
        SPUtil.setValue(Constant.USER_INFO, json.toString())
        startActivity()
    }
}
