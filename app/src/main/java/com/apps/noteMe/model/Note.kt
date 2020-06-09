package com.apps.noteMe.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "notes")
data class Note(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo(name = "user_id")
    val userId: Long = 0L,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "content")
    var content: String? = null,

    @ColumnInfo(name = "is_pinned")
    var isPinned: Boolean = false,

    @ColumnInfo(name = "note_list_id")
    var noteListId : Long

//    @ColumnInfo(name = "createdAt")
//    var createdAt: Date = Date(),
//
//    @ColumnInfo(name = "updatedAt")
//    var updatedAt: Date = Date()

)