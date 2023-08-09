package com.example.ghusers.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int,
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)
