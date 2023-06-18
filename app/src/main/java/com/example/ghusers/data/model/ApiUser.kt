package com.example.ghusers.data.model

import com.example.ghusers.data.entity.User
import com.google.gson.annotations.SerializedName

data class ApiUser(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

fun ApiUser.toDBUser() = User(login = login, avatarUrl = avatarUrl)
