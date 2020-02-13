package com.apps.noteMe.models

import androidx.room.Embedded
import androidx.room.Relation
import com.apps.noteMe.models.Note
import com.apps.noteMe.models.User

data class UserWithNotes(
    @Embedded val user: User,

    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )

    val notes: List<Note>
)