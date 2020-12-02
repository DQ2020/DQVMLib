package com.open.dqmvvm.login

import androidx.lifecycle.MutableLiveData
import com.open.dqmvvm.base.BaseViewModel
import com.open.dqmvvm.constant.Constant
import com.open.dqmvvm.log.log
import com.open.dqmvvm.main.MainActivity
import com.open.dqmvvm.net.Net
import com.open.dqmvvm.util.SPUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        }
    }

    fun rememberAccountAndPassword() {
    }

    fun login() = GlobalScope.launch {
        loading.postValue(true)
        delay(2000)
        Net.init().login()
                .apply {
                    name.log()
                    age.toString().log()
                    save()
                    next.postValue(HashMap<String,Any>().also{ it->
                        it["class"] = MainActivity::class.java
                        it["exit"] = true
                    })
                }
        loading.postValue(false)
    }

    private fun save(){
        val json = JSONObject()
        json.put("account", account.value)
        json.put("password", password.value)
        json.put("remPwd", remPwd.value)
        SPUtil.setValue(Constant.USER_INFO, json.toString())
    }
}
