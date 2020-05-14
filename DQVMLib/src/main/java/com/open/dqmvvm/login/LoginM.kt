package com.open.dqmvvm.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LoginM(val account:String, val password:String,val boolean:Int){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}