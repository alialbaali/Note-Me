package com.apps.noteMe.database

import androidx.room.*
import com.apps.noteMe.models.User
import com.apps.noteMe.models.UserWithNotes

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User

    @Transaction
    @Query("SELECT * FROM users")
    suspend fun getUsersWithNotes(): List<UserWithNotes>

}
