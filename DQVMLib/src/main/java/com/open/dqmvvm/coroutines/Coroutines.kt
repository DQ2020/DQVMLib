package com.open.dqmvvm.coroutines

import kotlinx.coroutines.*

class Coroutines {
}


//fun main(){
//    //开启一个协程
//    println("main start")
//    val job = GlobalScope.launch {
//        delay(3000L)
//        println("GlobalScope.launch")
//        delay(3000L)
//    }
//    println("main() after GlobalScope.launch")
//    runBlocking {
//        println("runBlocking start")
//        delay(10000L)
//        println("runBlocking end")
//    }
//    println("main() after runBlocking")
//}
//        main start
//        main() after GlobalScope.launch
//        runBlocking start
//        GlobalScope.launch
//        runBlocking end
//        main() after runBlocking


//fun main(){
//    //开启一个协程
//    println("main start")
//    val job = GlobalScope.launch {
//        delay(3000L)
//        println("GlobalScope.launch")
//        delay(3000L)
//    }
//    println("main() after GlobalScope.launch")
//     job.join()
//}

//fun main() = runBlocking {
//    //开启一个协程
//    println("main start")
//    launch {
//        delay(3000L)
//        println("GlobalScope.launch")
//        delay(3000L)
//    }
//    println("main() after GlobalScope.launch")
//}

//fun main() = runBlocking { // this: CoroutineScope
//    launch {
//        delay(200L)
//        println("Task from runBlocking")
//    }
//
//    coroutineScope { // 创建一个协程作用域
//        launch {
//            delay(500L)
//            println("Task from nested launch")
//        }
//
//        delay(100L)
//        println("Task from coroutine scope") // 这一行会在内嵌 launch 之前输出
//    }
//
//    println("Coroutine scope is over") // 这一行在内嵌 launch 执行完毕后才输出
//}

fun main() = runBlocking {
    val job = launch(Dispatchers.Default) {
        var i = 0
        while(i<100000 && isActive){
                print(i++)
        }
    }
    delay(5L)
    job.cancelAndJoin()
    println("stop")

    var a = Person("a",1)
    var b = Person("b",2)
    a = b.also {
        b=a
    }
    println(a)
    println(b)
}

data class Person(var name:String,var age:Int)


//fun main() = runBlocking {
//
//    val job = launch(Dispatchers.Default) {
//        val startTime = System.currentTimeMillis()
//        var nextPrintTime = startTime
//        var i = 0
//        while (i < 10) { // 一个执行计算的循环，只是为了占用 CPU
//            // 每秒打印消息两次
//            if (System.currentTimeMillis() >= nextPrintTime) {
//                println("job: I'm sleeping ${i++} ...")
//                nextPrintTime += 1000L
//            }
//        }
//    }
//    delay(5000L) // 等待一段时间
//    println("main: I'm tired of waiting!")
//    job.cancelAndJoin() // 取消一个作业并且等待它结束
//    println("main: Now I can quit.")
//}
