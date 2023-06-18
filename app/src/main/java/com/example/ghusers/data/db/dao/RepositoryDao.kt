package com.example.ghusers.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ghusers.data.db.entity.DbRepository

@Dao
interface RepositoryDao {

    @Insert
    suspend fun insertRepos(repos: List<DbRepository>)

    @Query("SELECT * FROM repository WHERE user_login = :login")
    suspend fun getAllByLogin(login: String): List<DbRepository>

}