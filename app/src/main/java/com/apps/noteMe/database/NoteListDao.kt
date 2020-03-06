package com.apps.noteMe.database

import androidx.room.*
import com.apps.noteMe.model.NoteList

@Dao
interface NoteListDao {

    @Insert
    fun insertNoteList(noteList: NoteList)

    @Update
    fun updateNoteList(noteList: NoteList)

    @Delete
    fun deleteNoteList(noteList: NoteList)

    @Query("SELECT * FROM note_lists")
    fun getNoteLists(): List<NoteList>

    @Query("SELECT * FROM note_lists WHERE id = :id")
    fun getNoteListById(id: Long): NoteList
}