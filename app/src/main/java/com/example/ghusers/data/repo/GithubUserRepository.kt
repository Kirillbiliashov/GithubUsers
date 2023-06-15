package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.model.User

interface GithubUserRepository {
    suspend fun getAllUsers(): List<User>
}

class GithubUserRepositoryImpl(
    private val apiService: GithubApiService
) : GithubUserRepository {
    override suspend fun getAllUsers(): List<User> = apiService.getUsers()
}

