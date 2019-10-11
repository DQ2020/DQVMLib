package com.open.dqmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.open.dqmvvm.BR
import com.open.dqmvvm.util.ILoading
import com.open.dqmvvm.util.Loading
import org.jetbrains.anko.toast

abstract class BaseActivity<BD : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), ILoading {
    lateinit var bind: BD
    val vm: VM get() = getViewModel()
    override val loading: Loading? by lazy {
        Loading(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, getView())
        bind.setVariable(getVmId(), vm)
        loading()
        init()
    }

    abstract fun getView(): Int

    open fun getVmId(): Int{
        return BR.viewModel
    }
    abstract fun getViewModel(): VM
    abstract fun init()

    private fun loading() {
        vm.loading.observe(this, Observer { loading ->
            when {
                loading -> {
                    show()
                }
                else -> {
                    dismiss()
                }
            }
        })
        lifecycle.addObserver(loading!!)
    }
}