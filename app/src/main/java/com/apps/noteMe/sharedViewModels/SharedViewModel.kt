package com.apps.noteMe.sharedViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.noteMe.database.NoteDao
import com.apps.noteMe.models.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SharedViewModel(private val noteDao: NoteDao) : ViewModel() {

    lateinit var notes: LiveData<List<Note>>

    private var isItNewNote = false

    val currentNote = MutableLiveData<Note>()

    val selectedItem = mutableListOf<Note>()

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            notes = noteDao.getNotes()
        }
    }


    fun delete(note: Note?) {
        viewModelScope.launch(context = Dispatchers.IO) {
            if (note == null) {
                if (!isItNewNote) {
                    noteDao.deleteNote(currentNote.value!!)
                }
            } else {
                noteDao.deleteNote(note)
            }
        }
    }

    fun save() {
        if (!(currentNote.value?.title.isNullOrBlank() && currentNote.value?.content.isNullOrBlank())) {
            if (isItNewNote) {
                insert(currentNote.value!!)
            } else {
                update(currentNote.value!!)
            }
        } else {
            currentNote.value = null
        }
    }

    private fun update(note: Note) {
        viewModelScope.launch(context = Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }

    fun insert(note: Note) {
        viewModelScope.launch(context = Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }

    fun getNoteById(id: Long) {
        if (id == 0L) {
            currentNote.value = Note()
            isItNewNote = true
        } else {
            viewModelScope.launch(context = Dispatchers.IO) {
                currentNote.postValue(noteDao.getNoteById(id))
                isItNewNote = false
            }
        }
    }
}