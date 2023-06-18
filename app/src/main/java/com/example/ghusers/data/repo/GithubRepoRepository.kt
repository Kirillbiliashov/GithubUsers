package com.example.ghusers.data.repo

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.db.dao.RepositoryDao
import com.example.ghusers.data.db.entity.DbRepository
import com.example.ghusers.data.api.model.ApiRepository

interface GithubRepoRepository {
    suspend fun getAllRepos(login: String): List<ApiRepository>

    suspend fun refreshReposCache(repos: List<DbRepository>)
}

class GithubRepoRepositoryImpl(
    private val apiService: GithubApiService,
    private val repositoryDao: RepositoryDao
) : GithubRepoRepository {
    override suspend fun getAllRepos(login: String) = apiService.getUserRepos(login)
    override suspend fun refreshReposCache(repos: List<DbRepository>) {
        repositoryDao.insertRepos(repos)
    }
}