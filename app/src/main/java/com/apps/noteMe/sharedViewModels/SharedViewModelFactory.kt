package com.apps.noteMe.sharedViewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.noteMe.repo.NoteListRepository
import com.apps.noteMe.repo.NoteRepository

class SharedViewModelFactory(private val noteRepository: NoteRepository, private val noteListRepository: NoteListRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(noteRepository, noteListRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}