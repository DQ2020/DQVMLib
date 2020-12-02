package com.open.dqmvvm.kotlin

import com.open.dqmvvm.net.INet
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit

fun main() {

    val arr = intArrayOf(20, 55, 3, 16, 60, 65, 8, 10, 70, 90)
    quickSort(arr, 0, arr.size -1)
}

fun quickSort(arr: IntArray, s: Int, e: Int) {
    var start = s
    var end = e
    val z = arr[s]
    println("start$start")
    println("end$end")
    println("z$z")
    while (start < end) {
        for (i in end downTo start) {
            end = i
            if (start == end){
                arr[end] = z
                break
            }
            if (arr[i] < z) {
                println("111第$i 个数 ${arr[i]} 小于$z 放在 $start 位置")
                arr[start] = arr[i]
                break
            }
        }
        for (i in start ..end) {
            start = i
            if (start == end){
                arr[end] = z
                break
            }
            if (arr[i] > z) {
                println("222第$i 个数 ${arr[i]} 大于$z  放在$end 位置")
                arr[end] = arr[i]
                break
            }
        }
        println("---------")
    }
    println("+++++++++++++++++++++++++")
    arr.forEachIndexed { ii,i ->
        println("$ii  = ".plus(i))
    }
    if (start + 1 < e)
        quickSort(arr, start + 1, e)
    if (s < start -1)
        quickSort(arr, s, start - 1)



    OkHttpClient.Builder().build().newCall(null).enqueue(null)

    Retrofit.Builder().build().create(INet::class.java).baidu()

    Observable.just("1").observeOn(Schedulers.newThread()).subscribe()
}


