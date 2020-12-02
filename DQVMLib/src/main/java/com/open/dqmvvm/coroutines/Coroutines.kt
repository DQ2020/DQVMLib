package com.open.dqmvvm.coroutines

import io.reactivex.disposables.Disposable
import kotlinx.coroutines.*
import java.net.ServerSocket

fun main() {
    println("start Coroutines 1")
    runBlocking(Dispatchers.IO) {
        delay(1000)
        println("in Coroutines 1")
    }
    println("start Coroutines 1 end")
    println("runBlocking启动的协程任务会阻断当前线程，直到该协程执行结束")
    println()

    println("start Coroutines 2")
    val launch = GlobalScope.launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("in Coroutines 2 before")
        delay(3000)
        println("in Coroutines 2 after")
    }
    println("start Coroutines 2 end")
    launch.start()
    Thread.sleep(1000)
    launch.cancel()
    println("cancel Coroutines 2  so without after")
    println()

    println("start Coroutines 3")
    GlobalScope.launch {
        println("start Coroutines 33")
        val async = GlobalScope.async(Dispatchers.IO, CoroutineStart.DEFAULT) {
            delay(3000)
            "GlobalScope.async"
        }

        val async2 = GlobalScope.async(Dispatchers.IO, CoroutineStart.DEFAULT) {
            delay(5000)
            "GlobalScope.async2"
        }
        println(async.await() + async2.await())
        println()
        println("start Coroutines 33 end")
    }
    println("start Coroutines 3 end")

    while (true){
    }
}

suspend fun wait(n:Long):String{
    delay(n)
    return n.toString()
}


fun testServerSocket(){
    val ss = ServerSocket(2020)
    println("ServerSocket accept")
    val s = ss.accept()
    val ins = s.getInputStream()
    val bytes = ByteArray(1024)
    while (true){
        ins.read(bytes)
        println(String(bytes,0,10))
    }
}