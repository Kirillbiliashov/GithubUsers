package com.example.ghusers.data.di

import com.example.ghusers.data.api.GithubApiService
import com.example.ghusers.data.repo.GithubRepoRepository
import com.example.ghusers.data.repo.GithubRepoRepositoryImpl
import com.example.ghusers.data.repo.GithubUserRepository
import com.example.ghusers.data.repo.GithubUserRepositoryImpl
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val githubRepoRepository: GithubRepoRepository
    val githubUserRepository: GithubUserRepository
}

class AppContainerImpl : AppContainer {
    private val URL = "https://api.github.com/"

    private val retrofit = Retrofit
        .Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    private val apiService: GithubApiService by lazy {
        retrofit.create(GithubApiService::class.java)
    }

    override val githubRepoRepository by lazy {
        GithubRepoRepositoryImpl(apiService)
    }

    override val githubUserRepository by lazy {
        GithubUserRepositoryImpl(apiService)
    }

}
