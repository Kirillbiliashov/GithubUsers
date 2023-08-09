package com.example.ghusers.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "repository_user",
    primaryKeys = ["repo_id", "user_id"]
)
data class RepositoryUser(
    @ColumnInfo(name = "repo_id")
    val repoId: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int
)
