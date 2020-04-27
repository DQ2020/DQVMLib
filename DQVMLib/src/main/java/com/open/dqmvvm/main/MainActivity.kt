package com.open.dqmvvm.main

import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.BR
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityMainBinding
import com.open.dqmvvm.log.L
import com.open.dqmvvm.util.DBUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding,MainVM>() {

    override fun getView(): Int {
        return R.layout.activity_main
    }

    override fun getVmId(): Int {
        return BR.m
    }

    override fun getViewModel(): MainVM {
        return ViewModelProvider(this)[MainVM::class.java]
    }

    override fun init() {
        val users = DBUtil.db.userDao().showUser()
        val sb = StringBuilder()
        for (u in users){
            sb.append(u.toString()).append("\n")
        }
        show.text = sb.toString()


        L.d(intent.getStringExtra("param1")!!)
    }
}