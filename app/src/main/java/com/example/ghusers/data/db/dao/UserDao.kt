package com.example.ghusers.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ghusers.data.db.entity.DbUser

@Dao
interface UserDao {

    @Insert
    suspend fun insertUsers(dbUsers: List<DbUser>)

    @Query("DELETE FROM user")
    suspend fun deleteUsers()

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<DbUser>

}