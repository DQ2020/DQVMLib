package com.open.dqmvvm.mvvm

import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityLoginBinding
import com.open.dqmvvm.main.MainVM

class MvvmActivity : BaseActivity<ActivityLoginBinding,MvvmVM>(){

    override fun getView(): Int {
        return R.layout.activity_mvvm
    }

    override fun getVmId(): Int {
        return 0
    }

    override fun getViewModel(): MvvmVM {
        return ViewModelProvider(this)[MvvmVM::class.java]
    }

    override fun init() {
    }
}