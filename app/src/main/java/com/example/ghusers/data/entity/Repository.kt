package com.example.ghusers.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.Date

@Entity(
    tableName = "repository",
    primaryKeys = ["user_login", "name"],
    foreignKeys = [ForeignKey(
        entity = User::class, parentColumns = ["login"],
        childColumns = ["user_login"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Repository(
    val name: String,
    val private: Boolean,
    val description: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "user_login")
    val userLogin: String
)
