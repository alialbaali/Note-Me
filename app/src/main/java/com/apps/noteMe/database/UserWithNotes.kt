package com.apps.noteMe.database

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithNotes(
    @Embedded val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )

    val notes: List<Note>
)