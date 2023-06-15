package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService

interface GithubUserRepository {
    fun getAllUsers(): List<String>
}

class GithubUserRepositoryImpl(
    private val apiService: GithubApiService
) : GithubUserRepository {
    override fun getAllUsers(): List<String> = apiService.getUsers()
}

