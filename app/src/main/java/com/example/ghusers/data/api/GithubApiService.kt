package com.example.ghusers.data.api

import com.example.ghusers.data.model.ApiRepository
import com.example.ghusers.data.model.ApiUser
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("users")
    suspend fun getUsers(): List<ApiUser>

    @GET("users/{login}/repos")
    suspend fun getUserRepos(@Path("login") login: String): List<ApiRepository>

}