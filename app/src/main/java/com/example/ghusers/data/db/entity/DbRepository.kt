package com.example.ghusers.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import java.util.Date

@Entity(
    tableName = "repository",
    primaryKeys = ["user_login", "name"],
    foreignKeys = [ForeignKey(
        entity = DbUser::class, parentColumns = ["login"],
        childColumns = ["user_login"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class DbRepository(
    val name: String,
    val private: Boolean,
    val description: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: Date,
    @ColumnInfo(name = "user_login")
    val userLogin: String
)
