package com.open.dqmvvm.share_element_anim

import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.BR
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.base.BaseViewModel
import com.open.dqmvvm.databinding.ActivityMvvmBinding

class ShareElementAnimActivity : BaseActivity<ActivityMvvmBinding,BaseViewModel>(){

    override fun getView(): Int {
        return R.layout.activity_share_element_anim
    }

    override fun getVmId(): Int {
        return BR.mvvm
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider(this)[BaseViewModel::class.java]
                .apply {
                    title.value = "*共享元素功能展示"
                }
    }

    override fun init() {
    }
}