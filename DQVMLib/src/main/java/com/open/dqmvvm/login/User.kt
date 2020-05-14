package com.open.dqmvvm.login

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        val account: String?,
        val password: String?,
        val remPwd:Boolean,
        val name:String,
        val age: Int,
        @Embedded val wife: Wife?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class Wife(val w_name: String?,val w_age: Int)