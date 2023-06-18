package com.example.ghusers.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ghusers.ui.uimodel.UiUser

@Entity(tableName = "user")
data class DbUser(
    @PrimaryKey
    val login: String,
    val avatarUrl: String
)

fun DbUser.toUiUser() = UiUser(login, avatarUrl)

