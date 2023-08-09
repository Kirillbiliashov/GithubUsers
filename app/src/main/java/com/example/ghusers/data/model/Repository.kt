package com.example.ghusers.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity("repository")
data class Repository(
    @PrimaryKey val id: Int,
    val name: String,
    val private: Boolean,
    val description: String?,
    @SerializedName("created_at") @ColumnInfo(name = "created_at")
    val createdAt: Date
)
