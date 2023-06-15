package com.example.ghusers.data.api

import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("")
    fun getUsers(): List<String>

    @GET("{login}/repos")
    fun getUserRepos(@Path("login") login: String): List<String>

}