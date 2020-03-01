package com.apps.noteMe.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.apps.noteMe.model.UserId

@Dao
interface UserIdDao {

    @Insert
    suspend fun insertUserId(userId: UserId)

    @Query("SELECT * FROM userId LIMIT 1")
    suspend fun getUserId(): UserId

}