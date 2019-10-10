package com.open.dqmvvm.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user:User)

    @Query("select * from user")
    fun showUser():List<User>
}