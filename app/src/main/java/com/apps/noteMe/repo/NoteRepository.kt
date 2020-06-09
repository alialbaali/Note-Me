package com.apps.noteMe.repo

import androidx.lifecycle.LiveData
import com.apps.noteMe.database.NoteDao
import com.apps.noteMe.database.UserIdDao
import com.apps.noteMe.model.Note
import com.apps.noteMe.network.NoteClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(
    val userIdDao: UserIdDao,
    private val noteDao: NoteDao,
    private val noteClient: NoteClient
) {

    suspend fun getNotes(): LiveData<List<Note>> {
        return withContext(Dispatchers.IO) {
            //            noteClient.getNotes(userIdDao.getUserId().id)
            noteDao.getNotes()
        }
    }

    suspend fun insertNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.insertNote(note)
//            noteClient.insertNote(note)
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(note)
//            noteClient.updateNote(note)
        }
    }

    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.deleteNote(note)
//            noteClient.deleteNote(note)
        }
    }

    suspend fun getNoteById(id: Long): Note {
        return withContext(Dispatchers.IO) {
            noteDao.getNoteById(id)
//            noteClient.getNoteById(id)
        }
    }
}