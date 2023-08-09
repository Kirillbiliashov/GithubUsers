package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.db.dao.RepositoryDao
import com.example.ghusers.data.model.Repository

interface GithubRepoRepository {
    suspend fun getAllApiRepos(login: String): List<Repository>

    suspend fun getAllCached(userLogin: String): List<Repository>

    suspend fun refreshReposCache(repos: List<Repository>)
}

class GithubRepoRepositoryImpl(
    private val apiService: GithubApiService,
    private val repositoryDao: RepositoryDao
) : GithubRepoRepository {
    override suspend fun getAllApiRepos(login: String) = apiService.getUserRepos(login)

    override suspend fun getAllCached(userLogin: String) = listOf<Repository>()

    override suspend fun refreshReposCache(repos: List<Repository>) {
        repositoryDao.insertRepos(repos)
    }
}