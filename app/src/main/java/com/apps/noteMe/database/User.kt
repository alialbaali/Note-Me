package com.apps.noteMe.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

data class User(

    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "email_address")
    var emailAddress: String

)