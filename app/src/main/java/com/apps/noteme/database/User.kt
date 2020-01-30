package com.apps.noteme.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")

class User(

    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "password")
    var password: String,

    @ColumnInfo(name = "emailAddress")
    var emailAddress: String

)