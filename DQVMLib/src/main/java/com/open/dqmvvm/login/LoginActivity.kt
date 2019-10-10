package com.open.dqmvvm.login

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityLoginBinding
import com.open.dqmvvm.main.MainActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginVM>() {

    override fun getView(): Int {
        return R.layout.activity_login
    }

    override fun getViewModel(): LoginVM {
        return ViewModelProviders.of(this).get(LoginVM::class.java)
    }

    override fun init() {
        vm.result.observe(this, Observer { result ->
            if (result) {
                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                finish()
            }
        })
    }
}
