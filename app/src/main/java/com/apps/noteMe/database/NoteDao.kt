package com.apps.noteMe.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.apps.noteMe.model.Note


@Dao
interface NoteDao {

    @Insert
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC ")
    fun getNotes(): LiveData<MutableList<Note>>

    @Query("SELECT * FROM notes WHERE Id = :id ")
    fun getNoteById(id: Long): Note

}