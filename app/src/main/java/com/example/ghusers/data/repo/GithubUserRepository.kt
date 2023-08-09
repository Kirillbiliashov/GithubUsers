package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.db.dao.UserDao
import com.example.ghusers.data.model.User

interface GithubUserRepository {
    suspend fun getAllUsers(): List<User>

    suspend fun getAllCached(): List<User>

    suspend fun refreshUserCache(dbUsers: List<User>)
}

class GithubUserRepositoryImpl(
    private val apiService: GithubApiService,
    private val userDao: UserDao
) : GithubUserRepository {
    override suspend fun getAllUsers(): List<User> = apiService.getUsers()

    override suspend fun getAllCached() = userDao.getAll()

    override suspend fun refreshUserCache(dbUsers: List<User>) {
        userDao.deleteUsers()
        userDao.insertUsers(dbUsers)
    }

}

