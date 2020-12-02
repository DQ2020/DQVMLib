package com.open.dqmvvm.web

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient


/**
 * Created by 2020 on 2019.
 */
abstract class BaseWebView : WebView {

    private val methodClass by lazy {
        methodsBody()
    }

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun init() {
        if (null != background) {
            setBackgroundColor(0)
            background.alpha = 0
        } else {
            setBackgroundColor(Color.TRANSPARENT)
        }
        val settings = settings
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.domStorageEnabled = true
        settings.databaseEnabled = true
        settings.javaScriptEnabled = true
        settings.allowFileAccess = true
        settings.textZoom = 100
        val dir = context.filesDir.absolutePath + "/cache"
        settings.setGeolocationEnabled(true)
        settings.setAppCachePath(dir) //设置AppCaches缓存路径
        settings.setAppCacheEnabled(true) //开启AppCaches功能
        settings.useWideViewPort = true
        settings.allowFileAccessFromFileURLs = false
        settings.allowUniversalAccessFromFileURLs = false
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webViewClient = WebViewClient()
        webChromeClient = BaseWebChromeClient()
        addJavascriptInterface(methodClass, "android")
    }

    private inner class BaseWebChromeClient : WebChromeClient() {
        private var video: View? = null
        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            if (!TextUtils.isEmpty(title)) {
                Log.d("DQ2020", "url title:$title")
            }
        }

        override fun onShowCustomView(
            view: View,
            callback: CustomViewCallback
        ) {
            video = view
            addView(video)
            super.onShowCustomView(view, callback)
        }

        override fun onShowCustomView(
            view: View,
            requestedOrientation: Int,
            callback: CustomViewCallback
        ) {
            video = view
            addView(video)
            super.onShowCustomView(view, callback)
        }

        override fun onHideCustomView() {
            super.onHideCustomView()
            removeView(video)
        }
    }

    fun loadMethod(name: String, param: String) {
        val method = "javascript:window.$name('$param')"
        loadUrl(method)
        Log.d("DQ2020", method)
    }

    fun loadMethod(name: String) {
        val method = "javascript:window.$name()"
        loadUrl(method)
        Log.d("DQ2020", method)
    }

    fun backPress() {
        if (canGoBack()) goBack()
        else (context as Activity).finish()
    }

    fun resume() {
    }

    fun onDestroy() {
        val p = parent
        if (p != null) (p as ViewGroup).removeView(this)
        stopLoading()
        settings.javaScriptEnabled = false
        removeAllViews()
        destroy()
    }

    abstract fun methodsBody():Any
}