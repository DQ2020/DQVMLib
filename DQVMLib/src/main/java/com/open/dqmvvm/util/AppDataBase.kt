package com.open.dqmvvm.util

import androidx.room.Database
import androidx.room.RoomDatabase
import com.open.dqmvvm.login.User
import com.open.dqmvvm.login.UserDao


@Database(entities = [User::class], version = 4)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}