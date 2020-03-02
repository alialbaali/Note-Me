package com.apps.noteMe.repo

import com.apps.noteMe.database.UserIdDao
import com.apps.noteMe.model.User
import com.apps.noteMe.network.UserClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserRepository(val userIdDao: UserIdDao, private val userClient: UserClient) {

    suspend fun signUp(value : String,user: User): Long {
        return withContext(Dispatchers.IO) {
            userClient.signUp("Basic $value", user)
        }
    }

    suspend fun signIn(value: String,user: User): Long {
        return withContext(Dispatchers.IO) {
            userClient.signIn("Basic $value", user)
        }
    }

    suspend fun updateUser(user: User) {
        withContext(Dispatchers.IO) {
            userClient.updateUser(user)
        }
    }

    suspend fun deleteUser(user: User) {
        withContext(Dispatchers.IO) {
            userClient.deleteUser(user)
        }
    }
}