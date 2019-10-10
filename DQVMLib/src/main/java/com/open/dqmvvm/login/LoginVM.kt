package com.open.dqmvvm.login

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.open.dqmvvm.base.BaseViewModel
import com.open.dqmvvm.constant.Constant
import com.open.dqmvvm.net.Net
import com.open.dqmvvm.util.DBUtil
import com.open.dqmvvm.util.L
import com.open.dqmvvm.util.SPUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginVM : BaseViewModel() {

    var account = ObservableField("")
    val password = ObservableField("")
    val rememberAccountAndPassword: MutableLiveData<Boolean> = MutableLiveData()
    val result = MutableLiveData<Boolean>()

    init {
        rememberAccountAndPassword.value = false
        result.value = false
        if (SPUtil.getString(Constant.REMEMBER_ACCOUNT).isNotEmpty()) {
            account.set(SPUtil.getString(Constant.REMEMBER_ACCOUNT))
        }
        if (SPUtil.getString(Constant.REMEMBER_PASSWORD).isNotEmpty()) {
            password.set(SPUtil.getString(Constant.REMEMBER_PASSWORD))
            rememberAccountAndPassword.value = true
        }
    }

    fun rememberAccountAndPassword() {
        when {
            rememberAccountAndPassword.value!! -> {
                SPUtil.setValue(Constant.REMEMBER_ACCOUNT, account.get()!!)
                SPUtil.setValue(Constant.REMEMBER_PASSWORD, password.get()!!)
            }
            else -> {
                SPUtil.setValue(Constant.REMEMBER_ACCOUNT, "")
                SPUtil.setValue(Constant.REMEMBER_PASSWORD, "")
            }
        }
    }

    fun login() = GlobalScope.launch(Dispatchers.Main) {
        L.d(Thread.currentThread().name)
        val res = Net.init().baidu()
        loading.value = true
        delay(3000)
        loading.value = false
        rememberAccountAndPassword()
        L.d("last" + Thread.currentThread().name)
        result.value = true
        addUser()
    }

    private fun addUser() {
        DBUtil.db.userDao().addUser(User("D", "Q", 20, Wife("H", 18)))
    }
}
