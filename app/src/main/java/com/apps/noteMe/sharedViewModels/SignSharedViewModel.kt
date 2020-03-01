package com.apps.noteMe.sharedViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apps.noteMe.model.User
import com.apps.noteMe.model.UserId
import com.apps.noteMe.repo.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException


class SignSharedViewModel(private val userRepository: UserRepository) : ViewModel() {

    lateinit var signNavigation: SignNavigation

    val currentUser = MutableLiveData<User>()

    init {
        currentUser.value = User()
    }

    fun signUp() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val id = userRepository.signUp(currentUser.value!!)
                userRepository.userIdDao.insertUserId(UserId(id))
                signNavigation.navigate()
            } catch (e: HttpException) {
            }

        }
    }


    fun signIn() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val id = userRepository.signIn(currentUser.value!!)
                userRepository.userIdDao.insertUserId(UserId(id))
                signNavigation.navigate()
            } catch (e: HttpException) {
            }
        }
    }
}

interface SignNavigation {
    fun navigate()
}