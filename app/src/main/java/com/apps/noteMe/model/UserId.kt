package com.apps.noteMe.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userId")
data class UserId (

    @PrimaryKey(autoGenerate = false)
    val id: Long
)

