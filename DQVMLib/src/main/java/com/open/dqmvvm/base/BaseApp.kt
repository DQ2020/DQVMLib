package com.open.dqmvvm.base

import android.app.Application
import com.open.dqmvvm.util.DBUtil
import com.open.dqmvvm.util.SPUtil

object BaseApp{

    fun init(context:Application){
        SPUtil.init(context)
        DBUtil.init(context)
    }
}