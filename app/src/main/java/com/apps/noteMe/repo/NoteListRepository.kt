package com.apps.noteMe.repo

import com.apps.noteMe.database.NoteListDao
import com.apps.noteMe.model.NoteList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteListRepository(private val noteListDao: NoteListDao) {

    suspend fun insertNoteList(noteList: NoteList) {
        withContext(Dispatchers.IO) {
            noteListDao.insertNoteList(noteList)
        }
    }

    suspend fun updateNoteList(noteList: NoteList) {
        withContext(Dispatchers.IO) {
            noteListDao.insertNoteList(noteList)
        }
    }

    suspend fun deleteNoteList(noteList: NoteList) {
        withContext(Dispatchers.IO) {
            noteListDao.deleteNoteList(noteList)
        }
    }

    suspend fun getNoteListById(id: Long): NoteList {
        return withContext(Dispatchers.IO) {
            noteListDao.getNoteListById(id)
        }
    }

    suspend fun getNoteLists(): List<NoteList> {
        return withContext(Dispatchers.IO) {
            noteListDao.getNoteLists()
        }
    }

}