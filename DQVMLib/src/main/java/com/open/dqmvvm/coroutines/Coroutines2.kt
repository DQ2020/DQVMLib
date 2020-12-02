package com.open.dqmvvm.coroutines

import java.net.Socket
import java.util.*
import kotlin.coroutines.suspendCoroutine

fun main() {
    val c = Socket("192.168.56.1",2020)
    val scanner = Scanner(System.`in`)
    println("cm:")
    while (true){
        val cm = scanner.nextLine()
        c.getOutputStream().write(cm.toByteArray())
    }
}