package com.example.ghusers.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Repository(
    val name: String,
    val private: Boolean,
    val description: String?,
    @SerializedName("created_at")
    val createdAt: Date
)
