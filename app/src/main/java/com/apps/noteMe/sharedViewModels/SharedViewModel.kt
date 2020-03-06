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


    lateinit var notes: LiveData<List<Note>>
    private var isItNewNote = false

    val currentNote = MutableLiveData<Note>()

    init {
        viewModelScope.launch(context = Dispatchers.IO) {
            //            notes = noteRepository.getNotes()
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
                    insertNote(currentNote.value!!)
                } else {
                    updateNote(currentNote.value!!)
                }
            } else {
                currentNote.postValue(null)
            }
        }
    }

    private fun insertNote(note: Note) {
        viewModelScope.launch(Dispatchers.Main) {
            noteRepository.insertNote(note)
        }
    }

    private fun updateNote(note: Note) {
        viewModelScope.launch(Dispatchers.Main) {
            noteRepository.updateNote(note)
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch(Dispatchers.Main) {
            if (id == 0L) {
                currentNote.value = Note(noteListId = 0)
                isItNewNote = true
            } else {
                currentNote.postValue(noteRepository.getNoteById(id))
                isItNewNote = false
            }
        }
    }
}