package com.movit.dqtest.web

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import com.movit.dqtest.BuildConfig
import com.open.dqmvvm.web.BaseWebView

class TestWebView : BaseWebView {

    constructor(context: Context?) : super(context) {
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    }

    override fun methodsBody(): Any = {
        @JavascriptInterface
        fun version(): String {
            return BuildConfig.VERSION_NAME
        }
    }
}