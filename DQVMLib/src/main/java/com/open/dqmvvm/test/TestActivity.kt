package com.open.dqmvvm.test

import android.app.Activity
import android.os.Bundle
import com.open.dqmvvm.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        labels.initLabels(listOf("高端","大气","上档次"))
    }

}
