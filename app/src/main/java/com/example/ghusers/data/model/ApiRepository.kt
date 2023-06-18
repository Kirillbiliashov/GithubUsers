package com.example.ghusers.data.model

import com.example.ghusers.data.entity.Repository
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ApiRepository(
    val name: String,
    val private: Boolean,
    val description: String?,
    @SerializedName("created_at")
    val createdAt: Date
)

fun ApiRepository.toDBRepository(user: ApiUser) = Repository(
    name = name,
    private = private,
    description = description,
    createdAt = createdAt,
    userLogin = user.login
)
