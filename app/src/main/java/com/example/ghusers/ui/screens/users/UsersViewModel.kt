package com.example.ghusers.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.model.ApiUser
import com.example.ghusers.data.repo.GithubUserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UsersUIState(
    val apiUsers: List<ApiUser> = listOf()
)


class UsersViewModel(private val usersRepo: GithubUserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUIState())
    val uiState: StateFlow<UsersUIState> = _uiState

    init {
        loadGithubUsersFromApi()
    }

    private fun loadGithubUsersFromApi() {
        viewModelScope.launch {
            val apiData = usersRepo.getAllUsers()
            _uiState.update { it.copy(apiUsers = apiData) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("on cleared")
    }

}