package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.dao.RepositoryDao
import com.example.ghusers.data.entity.Repository
import com.example.ghusers.data.model.ApiRepository

interface GithubRepoRepository {
    suspend fun getAllRepos(login: String): List<ApiRepository>

    suspend fun refreshReposCache(repos: List<Repository>)
}

class GithubRepoRepositoryImpl(
    private val apiService: GithubApiService,
    private val repositoryDao: RepositoryDao
) : GithubRepoRepository {
    override suspend fun getAllRepos(login: String) = apiService.getUserRepos(login)
    override suspend fun refreshReposCache(repos: List<Repository>) {
        repositoryDao.insertRepos(repos)
    }
}