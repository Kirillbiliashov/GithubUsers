package com.example.ghusers

import android.app.Application
import com.example.ghusers.data.di.AppContainer
import com.example.ghusers.data.di.AppContainerImpl

class GithubUsersApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl()
    }
}