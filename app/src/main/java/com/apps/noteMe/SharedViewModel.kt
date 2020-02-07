package com.apps.noteMe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.noteMe.database.Note
import com.apps.noteMe.database.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SharedViewModel(private val noteDao: NoteDao) : ViewModel() {

    lateinit var notes: LiveData<List<Note>>

    private var isItNewNote = false
    private lateinit var currentNote: Note


    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            notes = noteDao.getNotes()
        }
    }


    val title = MutableLiveData<String>()
    val content = MutableLiveData<String>()

    fun delete() {

    }

    fun save() {

        currentNote.title = title.value
        currentNote.content = content.value

        if (isItNewNote) {
            insert(currentNote)
        } else {
            update(currentNote)
        }

    }

    private fun update(note: Note) {
        viewModelScope.launch(context = Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }

    private fun insert(note: Note) {
        viewModelScope.launch(context = Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }


    fun getNoteById(id: Long) {
        if (id == 0L) {
            currentNote = Note()
            isItNewNote = true
        } else {
            viewModelScope.launch(context = Dispatchers.IO) {
                currentNote = noteDao.getNoteById(id)
                title.postValue(currentNote.title)
                content.postValue(currentNote.content)
                isItNewNote = false
            }
        }
    }
}