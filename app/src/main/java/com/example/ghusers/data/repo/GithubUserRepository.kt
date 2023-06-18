package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.dao.UserDao
import com.example.ghusers.data.entity.User
import com.example.ghusers.data.model.ApiUser

interface GithubUserRepository {
    suspend fun getAllUsers(): List<ApiUser>

    suspend fun refreshUserCache(users: List<User>)
}

class GithubUserRepositoryImpl(
    private val apiService: GithubApiService,
    private val userDao: UserDao
) : GithubUserRepository {
    override suspend fun getAllUsers(): List<ApiUser> = apiService.getUsers()
    override suspend fun refreshUserCache(users: List<User>) {
        userDao.deleteUsers()
        userDao.insertUsers(users)
    }

}

