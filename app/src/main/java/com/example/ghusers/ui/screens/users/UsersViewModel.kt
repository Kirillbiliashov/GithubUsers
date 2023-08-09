package com.example.ghusers.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.model.User
import com.example.ghusers.data.repo.GithubUserRepository
import com.example.ghusers.ui.screens.util.LoadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UsersUIState(
    val users: List<User> = listOf(),
    val loadState: LoadState = LoadState.LOADING_CACHE,
    val userMessage: String? = null
)

class UsersViewModel(private val usersRepo: GithubUserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUIState())
    val uiState: StateFlow<UsersUIState> = _uiState

    init {
        viewModelScope.launch {
            loadGithubUsersFromCache()
            loadGithubUsersFromApi()

        }

    }

    private suspend fun loadGithubUsersFromCache() {
        val dbUsers = usersRepo.getAllCached()
        _uiState.update {
            it.copy(
                users = dbUsers,
                loadState = LoadState.LOADED
            )
        }
    }

    private suspend fun loadGithubUsersFromApi() {
        _uiState.update { it.copy(loadState = LoadState.LOADING_SERVER) }
        try {
            val apiData = usersRepo.getAllUsers()
            _uiState.update {
                it.copy(
                    users = apiData,
                    loadState = LoadState.LOADED
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    loadState = LoadState.LOADED,
                    userMessage = "Unable to load data from server, so cached data is displayed instead"
                )
            }
        }
    }

    fun clearSnackbarMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }

}