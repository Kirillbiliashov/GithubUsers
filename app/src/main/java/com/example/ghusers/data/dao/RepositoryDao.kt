package com.example.ghusers.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghusers.data.entity.Repository
import com.example.ghusers.data.entity.User

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insertRepos(repos: List<Repository>)

}