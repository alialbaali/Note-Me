package com.apps.noteMe.network

import com.apps.noteMe.model.Note
import retrofit2.http.*

interface NoteClient {

    @POST("note-service/insert-note")
    suspend fun insertNote(@Body note: Note)

    @PUT("note-service/update-note")
    suspend fun updateNote(@Body note: Note)

    @GET("note-service/get-note-by-id/{id}")
    suspend fun getNoteById(@Path("id") id: Long): Note

    @GET("note-service/get-notes/{id}")
    suspend fun getNotes(@Path("id") userId: Long): MutableList<Note>

    @DELETE("note-service/delete-note")
    suspend fun deleteNote(@Body note: Note)

}