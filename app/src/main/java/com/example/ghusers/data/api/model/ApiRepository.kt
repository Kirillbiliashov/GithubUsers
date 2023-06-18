package com.example.ghusers.data.api.model

import com.example.ghusers.data.db.entity.DbRepository
import com.example.ghusers.ui.uimodel.UiRepository
import com.example.ghusers.utils.AppUtils.toFormattedString
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ApiRepository(
    val name: String,
    val private: Boolean,
    val description: String?,
    @SerializedName("created_at")
    val createdAt: Date
)

fun ApiRepository.toUiRepository() = UiRepository(
    name = name,
    dateStr = createdAt.toFormattedString(),
    descriptionText = description ?: "No description provided.",
    private = private
)

fun ApiRepository.toDBRepository(user: ApiUser) = DbRepository(
    name = name,
    private = private,
    description = description,
    createdAt = createdAt,
    userLogin = user.login
)
