package com.open.dqmvvm.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.open.dqmvvm.BR
import com.open.dqmvvm.log.L
import com.open.dqmvvm.util.ILoading
import com.open.dqmvvm.util.Loading

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
        ui()
        init()
    }

    abstract fun getView(): Int
    abstract fun getVmId(): Int
    abstract fun getViewModel(): VM
    abstract fun init()

    private fun ui() {
        loading()
        nextActivity()
    }

    private fun nextActivity() {
        vm.next.observe(this, Observer { map ->
            if (null == map) return@Observer
            val intent = Intent(this, map["class"] as Class<*>)
            for (key in map.keys) {
                when (map[key]) {
                    is String -> intent.putExtra(key, map[key] as String)
                    is Int -> intent.putExtra(key, map[key] as Int)
                    is Long -> intent.putExtra(key, map[key] as Long)
                    is Double -> intent.putExtra(key, map[key] as Double)
                    is Float -> intent.putExtra(key, map[key] as Float)
                    else -> L.e("nextActivity::unKnown data type!")
                }
            }
            startActivity(intent)
            if (null == map["exit"] || map["exit"] as Boolean) finish()
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