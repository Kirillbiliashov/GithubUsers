package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.db.dao.UserDao
import com.example.ghusers.data.db.entity.DbUser
import com.example.ghusers.data.api.model.ApiUser

interface GithubUserRepository {
    suspend fun getAllUsers(): List<ApiUser>

    suspend fun getAllCached(): List<DbUser>

    suspend fun refreshUserCache(dbUsers: List<DbUser>)
}

class GithubUserRepositoryImpl(
    private val apiService: GithubApiService,
    private val userDao: UserDao
) : GithubUserRepository {
    override suspend fun getAllUsers(): List<ApiUser> = apiService.getUsers()

    override suspend fun getAllCached() = userDao.getAll()

    override suspend fun refreshUserCache(dbUsers: List<DbUser>) {
        userDao.deleteUsers()
        userDao.insertUsers(dbUsers)
    }

}

