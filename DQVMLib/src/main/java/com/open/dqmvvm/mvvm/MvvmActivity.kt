package com.open.dqmvvm.mvvm

import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.BR
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityMvvmBinding

class MvvmActivity : BaseActivity<ActivityMvvmBinding,MvvmVM>(){

    override fun getView(): Int {
        return R.layout.activity_mvvm
    }

    override fun getVmId(): Int {
        return BR.mvvm
    }

    override fun getViewModel(): MvvmVM {
        return ViewModelProvider(this)[MvvmVM::class.java]
                .apply {
                    text.value = "this is mvvm activity"
                }
    }

    override fun init() {
    }
}