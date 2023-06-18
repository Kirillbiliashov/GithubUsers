package com.example.ghusers.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.ghusers.data.db.entity.DbRepository

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insertRepos(repos: List<DbRepository>)

}