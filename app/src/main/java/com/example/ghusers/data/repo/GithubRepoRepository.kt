package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService

interface GithubRepoRepository {
    fun getAllRepos(login: String): List<String>
}

class GithubRepoRepositoryImpl(
    private val apiService: GithubApiService
) : GithubRepoRepository {
    override fun getAllRepos(login: String) = apiService.getUserRepos(login)
}