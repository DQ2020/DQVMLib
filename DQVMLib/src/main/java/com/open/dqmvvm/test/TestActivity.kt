package com.open.dqmvvm.test

import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.BR
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityTestBinding
import com.open.dqmvvm.log.L
import com.open.dqmvvm.v.pickers.Pickers
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity<ActivityTestBinding, TestVM>() {

    override fun getView(): Int {
        return R.layout.activity_test
    }

    override fun getVmId(): Int {
        return BR.test
    }

    override fun getViewModel(): TestVM {
        return ViewModelProvider(this).get(TestVM::class.java)
    }

    override fun init() {
        testLabels()
        testPickers()
    }

    private fun testPickers() {
        Pickers(this).addYearWheel { item ->
            L.d("$item")
        }.show()
    }

    private fun testLabels() {
        labels.initLabels(arrayListOf("test"))
    }
}
