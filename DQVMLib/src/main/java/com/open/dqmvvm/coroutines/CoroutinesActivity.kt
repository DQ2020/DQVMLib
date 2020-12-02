package com.open.dqmvvm.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.open.dqmvvm.R
import kotlinx.coroutines.*

class CoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutines)

        runBlocking {
            withContext(Dispatchers.IO){
                count("1")
            }
        }

        val job = GlobalScope.launch(Dispatchers.IO,CoroutineStart.LAZY){
            count("2")
        }
        job.start()
        job.cancel()

        count("3")


    }

    private fun count(no:String){
        println(no+Thread.currentThread().name)
    }
}
