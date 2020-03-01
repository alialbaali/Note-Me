package com.apps.noteMe.network

import com.apps.noteMe.database.NoteDao
import com.apps.noteMe.database.UserIdDao
import com.apps.noteMe.repo.NoteRepository
import com.apps.noteMe.repo.UserRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

private const val BASE_URL = "http://192.168.1.100:8080/"

private val logger by lazy {
    HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BASIC
    }
}

private val okHttpClient by lazy {
    OkHttpClient.Builder()
        .addInterceptor(logger)
        .build()
}

private val moshi: Moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit: Retrofit by lazy {
    Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
}

private object Clients {
    val userClient: UserClient by lazy {
//        Timber.i("userClient init")
        retrofit.create(UserClient::class.java)
    }

    val noteClient: NoteClient by lazy {
//        Timber.i("noteClient init")
        retrofit.create(NoteClient::class.java)
    }
}

object DAOs {
    lateinit var noteDao: NoteDao
    lateinit var userIdDao: UserIdDao
}

object Repos {
    val userRepository: UserRepository by lazy {
        UserRepository(DAOs.userIdDao, Clients.userClient)
    }
    val noteRepository: NoteRepository by lazy {
        NoteRepository(DAOs.userIdDao, DAOs.noteDao, Clients.noteClient)
    }
}