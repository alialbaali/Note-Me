package com.apps.noteMe.sharedViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.noteMe.database.NoteDao

class SharedViewModelFactory(private val noteDao: NoteDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}