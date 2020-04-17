package com.open.dqmvvm.util

import android.content.Context
import androidx.room.Room
import com.open.dqmvvm.log.L

/**
 * use method first:
 * DBUtil.init(context)
 */
object DBUtil {
    lateinit var db: AppDataBase

    fun init(context: Context) {
        db = Room.databaseBuilder(context, AppDataBase::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        L.d("DBUtil init:$db")
    }
}