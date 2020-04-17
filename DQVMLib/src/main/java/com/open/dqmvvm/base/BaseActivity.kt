package com.open.dqmvvm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.open.dqmvvm.BR
import com.open.dqmvvm.login.LoginActivity
import com.open.dqmvvm.util.ILoading
import com.open.dqmvvm.util.Loading
import org.jetbrains.anko.startActivity

abstract class BaseActivity<BD : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(), ILoading {
    lateinit var bind: BD
    val vm: VM get() = getViewModel()
    override val loading: Loading? by lazy {
        Loading(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, getView())
        bind.setVariable(BR.viewModel, vm)
        ui()
        init()
    }

    abstract fun getView(): Int

    open fun getVmId(): Int{
        return BR.viewModel
    }
    abstract fun getViewModel(): VM
    abstract fun init()

    private fun ui(){
        nextActivity()
        loading()
    }

    private fun nextActivity() {
        vm.next.observe(this, Observer { bundle ->
            if (null == bundle){
                startActivity<LoginActivity>()
            }
        })
        lifecycle.addObserver(loading!!)
    }

    private fun loading() {
        vm.loading.observe(this, Observer { value ->
            when {
                value -> {
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