package com.example.ghusers.data.api.model

import com.example.ghusers.data.db.entity.DbUser
import com.example.ghusers.ui.uimodel.UiUser
import com.google.gson.annotations.SerializedName

data class ApiUser(
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)

fun ApiUser.toDBUser() = DbUser(login = login, avatarUrl = avatarUrl)

fun ApiUser.toUiUser() = UiUser(login, avatarUrl)
