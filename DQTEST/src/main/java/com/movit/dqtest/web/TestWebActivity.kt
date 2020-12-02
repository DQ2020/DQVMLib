package com.movit.dqtest.web

import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.movit.dqtest.BR
import com.movit.dqtest.R
import com.movit.dqtest.databinding.ActivityTestWebBinding
import com.open.dqmvvm.web.BaseWebActivity
import com.open.dqmvvm.web.BaseWebView
import kotlinx.android.synthetic.main.activity_test_web.*

@Route(path = "/test/web")
class TestWebActivity : BaseWebActivity<ActivityTestWebBinding, TestWebVM>() {

    override fun initWeb(): BaseWebView {
        return web
    }

    override fun getView(): Int {
        return R.layout.activity_test_web
    }

    override fun getVmId(): Int {
        return BR.tw
    }

    override fun getViewModel(): TestWebVM {
        return ViewModelProvider(this)[TestWebVM::class.java]
    }

    override fun init() {
        (window.decorView as ViewGroup).addView(Button(this).apply {
            layoutParams = ViewGroup.LayoutParams(300,300)
            setOnClickListener {
                ARouter.getInstance().build("/lib/triangle3").navigation()
            }
        })
    }
}
