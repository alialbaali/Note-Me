package com.apps.noteme.signInUI

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.apps.noteme.database.UserDao

class SignInViewModelFactory(
    private val userDao: UserDao
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(userDao) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}