package com.open.dqmvvm.util

import android.content.Context
import androidx.room.Room


object DBUtil {

    lateinit var db: AppDataBase

    fun init(context: Context) {
        db = Room.databaseBuilder(context, AppDataBase::class.java, "app.db")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
    }
}