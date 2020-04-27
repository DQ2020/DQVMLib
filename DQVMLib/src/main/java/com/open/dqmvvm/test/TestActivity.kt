package com.open.dqmvvm.test

import androidx.lifecycle.ViewModelProvider
import com.open.dqmvvm.BR
import com.open.dqmvvm.R
import com.open.dqmvvm.base.BaseActivity
import com.open.dqmvvm.databinding.ActivityTestBinding
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity<ActivityTestBinding,TestVM>() {

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
        labels.initLabels(arrayListOf("test"))
    }
}
