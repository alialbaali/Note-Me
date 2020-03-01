package com.apps.noteMe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apps.noteMe.model.Note
import com.apps.noteMe.model.UserId

@Database(entities = [Note::class, UserId::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract val noteDao: NoteDao
    abstract val userIdDao: UserIdDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "AppDatabase"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}