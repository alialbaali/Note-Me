package com.apps.noteMe.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apps.noteMe.models.Note
import com.apps.noteMe.models.User

@Database(entities = [User::class, Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {


    abstract val noteDao: NoteDao
    abstract val userDao: UserDao

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