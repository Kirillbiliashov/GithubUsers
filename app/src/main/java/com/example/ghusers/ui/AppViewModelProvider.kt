package com.example.ghusers.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ghusers.GithubUsersApplication
import com.example.ghusers.ui.screens.repos.ReposViewModel
import com.example.ghusers.ui.screens.users.UsersViewModel


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            UsersViewModel(usersRepo = ghUsersApp().container.githubUserRepository)
        }
        initializer {
            ReposViewModel(
                savedStateHandle = this.createSavedStateHandle(),
                githubRepoRepository = ghUsersApp().container.githubRepoRepository
            )
        }
    }
}

fun CreationExtras.ghUsersApp(): GithubUsersApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as GithubUsersApplication)
