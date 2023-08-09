package com.example.ghusers.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ghusers.data.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUsers(dbUsers: List<User>)

    @Query("DELETE FROM user")
    suspend fun deleteUsers()

    @Query("SELECT * FROM user")
    suspend fun getAll(): List<User>

}