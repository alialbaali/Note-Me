package com.apps.noteMe.network

import com.apps.noteMe.model.User
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface UserClient {

    @GET("user-service/get-users")
    suspend fun getUsers(): List<User>

    @POST("user-service/sign-up")
    suspend fun signUp(@Body user: User): Long

    @POST("user-service/sign-in")
    suspend fun signIn(@Body user: User): Long

    @POST("user-service/delete-user")
    suspend fun updateUser(@Body user: User): Long?

    @DELETE("user-service/update-user")
    suspend fun deleteUser(@Body user: User)

}