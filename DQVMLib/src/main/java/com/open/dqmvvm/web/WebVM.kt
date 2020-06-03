package com.open.dqmvvm.web

import android.util.Log
import android.view.View
import android.webkit.WebView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.open.dqmvvm.base.BaseViewModel

abstract class WebVM : BaseViewModel() {

    val url by lazy {
        MutableLiveData(initUrl())
    }

    abstract fun initUrl():String

    object WebEvent{
        @JvmStatic
        @BindingAdapter(value = ["app:url"])
        fun url(web:WebView,url:String){
            web.loadUrl(url)
            Log.d("DQ2020",url)
        }
    }
}