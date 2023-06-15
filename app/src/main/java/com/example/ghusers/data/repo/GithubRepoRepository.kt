package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.model.Repository

interface GithubRepoRepository {
    suspend fun getAllRepos(login: String): List<Repository>
}

class GithubRepoRepositoryImpl(
    private val apiService: GithubApiService
) : GithubRepoRepository {
    override suspend fun getAllRepos(login: String) = apiService.getUserRepos(login)
}