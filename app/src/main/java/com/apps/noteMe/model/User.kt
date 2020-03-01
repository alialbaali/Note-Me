package com.apps.noteMe.model

import com.squareup.moshi.Json

data class User(

    @Json(name = "id")
    val id: Long = 0L,

    @Json(name = "name")
    var name: String? = null,

    @Json(name = "password")
    var password: String? = null,

    @Json(name = "email")
    var email: String? = null
)