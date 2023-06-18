package com.example.ghusers.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.api.model.toUiUser
import com.example.ghusers.data.db.entity.DbUser
import com.example.ghusers.data.db.entity.toUiUser
import com.example.ghusers.data.repo.GithubUserRepository
import com.example.ghusers.ui.screens.util.LoadState
import com.example.ghusers.ui.uimodel.UiUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.min


private const val USERS_PER_PAGE = 7

data class UsersUIState(
    private val _users: List<UiUser> = listOf(),
    val currentPage: Int = 1,
    val loadState: LoadState = LoadState.LOADING_CACHE
) {
    val users: List<UiUser>
        get() =
            if (_users.size < USERS_PER_PAGE) _users else
                _users.subList(
                    (currentPage - 1) * USERS_PER_PAGE,
                    min(currentPage * USERS_PER_PAGE, _users.size)
                )
    val hasPrevPage: Boolean
        get() = currentPage > 1
    val hasNextPage: Boolean
        get() = _users.size > currentPage * USERS_PER_PAGE
}


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
                    _users = dbUsers.map(DbUser::toUiUser),
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
                        _users = apiData.map { user -> user.toUiUser() },
                        loadState = LoadState.LOADED
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loadState = LoadState.LOADED) }
            }
        }
    }

    fun moveToNextPage() {
        val currentPage = _uiState.value.currentPage
        _uiState.update { it.copy(currentPage = currentPage + 1) }
    }

    fun moveToPrevPage() {
        val currentPage = _uiState.value.currentPage
        _uiState.update { it.copy(currentPage = currentPage - 1) }
    }

}