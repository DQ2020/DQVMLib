package com.open.dqmvvm.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.BR
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginVM>() {

    override fun getView(): Int {
        return R.layout.activity_login
    }

    override fun getVmId(): Int {
        return BR.viewModel
    }

    override fun getViewModel(): LoginVM {
        return ViewModelProvider(this)[LoginVM::class.java]
    }

    override fun init() {
        vm.remPwd.observe(this, Observer {

        })
    }

}
