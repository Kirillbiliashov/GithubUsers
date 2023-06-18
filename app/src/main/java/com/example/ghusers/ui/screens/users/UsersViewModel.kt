package com.example.ghusers.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.api.model.ApiUser
import com.example.ghusers.data.api.model.toUiUser
import com.example.ghusers.data.db.entity.DbUser
import com.example.ghusers.data.db.entity.toUiUser
import com.example.ghusers.data.repo.GithubUserRepository
import com.example.ghusers.ui.uimodel.UiUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UsersUIState(
    val users: List<UiUser> = listOf(),
    val loadState: LoadState = LoadState.LOADING_CACHE
)


class UsersViewModel(private val usersRepo: GithubUserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UsersUIState())
    val uiState: StateFlow<UsersUIState> = _uiState

    init {
        loadGithubUsersFromCache()
        loadGithubUsersFromApi()
    }

    private fun loadGithubUsersFromCache() {
        viewModelScope.launch {
            val dbUsers = usersRepo.getAllCached()
            _uiState.update {
                it.copy(
                    users = dbUsers.map(DbUser::toUiUser),
                    loadState = LoadState.LOADED
                )
            }
        }
    }

    private fun loadGithubUsersFromApi() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = LoadState.LOADING_SERVER) }
            try {
                val apiData = usersRepo.getAllUsers()
                _uiState.update {
                    it.copy(
                        users = apiData.map { user -> user.toUiUser() },
                        loadState = LoadState.LOADED
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loadState = LoadState.LOADED) }
            }
        }
    }

}