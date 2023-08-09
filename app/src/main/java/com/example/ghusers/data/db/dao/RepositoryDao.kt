package com.example.ghusers.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ghusers.data.model.Repository

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insertRepos(repos: List<Repository>)

}