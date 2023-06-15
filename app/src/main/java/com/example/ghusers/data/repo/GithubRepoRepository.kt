package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService

interface GithubRepoRepository {
    suspend fun getAllRepos(login: String): List<String>
}

class GithubRepoRepositoryImpl(
    private val apiService: GithubApiService
) : GithubRepoRepository {
    override suspend fun getAllRepos(login: String) = apiService.getUserRepos(login)
}