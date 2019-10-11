package com.open.dqmvvm.main

import androidx.lifecycle.ViewModelProviders
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityMainBinding
import com.open.dqmvvm.login.LoginVM
import com.open.dqmvvm.util.DBUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<ActivityMainBinding,LoginVM>() {

    override fun getView(): Int {
        return R.layout.activity_main
    }

    override fun getViewModel(): LoginVM {
        return ViewModelProviders.of(this).get(LoginVM::class.java)
    }

    override fun init() {
        val users = DBUtil.db.userDao().showUser()
        val sb = StringBuilder()
        for (u in users){
            sb.append(u.toString()).append("\n")
        }
        show.text = sb.toString()
    }
}