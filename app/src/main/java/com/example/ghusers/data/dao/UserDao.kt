package com.example.ghusers.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ghusers.data.entity.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUsers(users: List<User>)

    @Query("DELETE FROM user")
    suspend fun deleteUsers()

}