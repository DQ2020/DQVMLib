package com.open.dqmvvm.web

import androidx.databinding.ViewDataBinding
import com.open.dqmvvm.base.BaseActivity

abstract class BaseWebActivity<W: ViewDataBinding,VM :WebVM> : BaseActivity<W,VM>() {

    private val web by lazy {
        initWeb()
    }

    abstract fun initWeb():BaseWebView

    override fun onResume() {
        super.onResume()
        web.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        web.onDestroy()
    }

    override fun onBackPressed() {
        web.backPress()
    }
}
