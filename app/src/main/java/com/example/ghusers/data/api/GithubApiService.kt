package com.example.ghusers.data.api

import com.example.ghusers.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("users/{login}/repos")
    suspend fun getUserRepos(@Path("login") login: String): List<String>

}