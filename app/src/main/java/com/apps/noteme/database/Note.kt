package com.apps.noteme.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "notes")

class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "Body")
    var content: String? = null,

    @ColumnInfo(name = "isPinned")
    var isPinned: Boolean = false

//    @ColumnInfo(name = "createdAt")
//    var createdAt: Date = Date(),
//
//    @ColumnInfo(name = "updatedAt")
//    var updatedAt: Date = Date()

)