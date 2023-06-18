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
    val apiUsers: List<ApiUser> = listOf(),
    val loadState: LoadState = LoadState.LOADING
)


class UsersViewModel(private val usersRepo: GithubUserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUIState())
    val uiState: StateFlow<UsersUIState> = _uiState

    init {
        loadGithubUsersFromApi()
    }

    fun loadGithubUsersFromApi() {
        viewModelScope.launch {
            try {
                val apiData = usersRepo.getAllUsers()
                _uiState.update { it.copy(apiUsers = apiData, loadState = LoadState.LOADED) }
            } catch (e: Exception) {
                _uiState.update { it.copy(loadState = LoadState.ERROR) }
            }
        }
    }

}