package com.open.dqmvvm.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityLoginBinding
import com.open.dqmvvm.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginVM>() {

    override fun getView(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginVM {
        return ViewModelProvider(this)[LoginVM::class.java]
    }

    override fun init() {
        vm.remPwd.observe(this, Observer {

        })
    }

    fun success(){
        startActivity(Intent(this@LoginActivity,MainActivity::class.java))
        finish()
    }
}
