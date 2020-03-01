package com.apps.noteMe.sharedViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.noteMe.model.Note
import com.apps.noteMe.repo.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SharedViewModel(private val noteRepository: NoteRepository) : ViewModel() {


    lateinit var notes: LiveData<MutableList<Note>>

    private var isItNewNote = false

    val currentNote = MutableLiveData<Note>()

//    val selectedItem = mutableListOf<Note>()

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            notes = noteRepository.getNotes()
        }
    }


    fun delete(note: Note?) {
        viewModelScope.launch(context = Dispatchers.Main) {
            if (note == null) {
                if (!isItNewNote) {
                    noteRepository.deleteNote(currentNote.value!!)
                }
            } else {
                noteRepository.deleteNote(note)
            }
        }
    }

    fun save() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!(currentNote.value?.title.isNullOrBlank() && currentNote.value?.content.isNullOrBlank())) {
                if (isItNewNote) {
                    noteRepository.insertNote(currentNote.value!!)
                } else {
                    noteRepository.updateNote(currentNote.value!!)
                }
            } else {
                currentNote.value = null
            }
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            if (id == 0L) {
                currentNote.value = Note(userId = noteRepository.userIdDao.getUserId().id)
                isItNewNote = true
            } else {
                currentNote.postValue(noteRepository.getNoteById(id))
                isItNewNote = false
            }
        }
    }
}