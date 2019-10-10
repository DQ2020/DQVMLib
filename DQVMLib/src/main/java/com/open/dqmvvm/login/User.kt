package com.open.dqmvvm.login

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
        val firstName: String?,
        val lastName: String?,
        val age: Int,
        @Embedded val wife: Wife?
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    override fun toString(): String {
        return "UID:$uid\nF:$firstName\nL:$lastName\nAge:$age\nWife:${wife?.name}"
    }
}

data class Wife(val name: String?,val w_age: Int)