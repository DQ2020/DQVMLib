package com.movit.dqtest.web

import com.open.dqmvvm.web.WebVM

class TestWebVM :WebVM() {
    override fun initUrl(): String {
        return "https://www.baidu.com"
    }
}