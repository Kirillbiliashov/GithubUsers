package com.example.ghusers.ui.screens.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.api.model.toUiUser
import com.example.ghusers.data.db.entity.DbUser
import com.example.ghusers.data.db.entity.toUiUser
import com.example.ghusers.data.repo.GithubUserRepository
import com.example.ghusers.ui.screens.util.LoadState
import com.example.ghusers.ui.screens.util.PageableUIState
import com.example.ghusers.ui.screens.util.PageableViewModel
import com.example.ghusers.ui.uimodel.UiUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.min

data class UsersUIState(
    override val data: List<UiUser> = listOf(),
    override val currentPage: Int = 1,
    val loadState: LoadState = LoadState.LOADING_CACHE,
    val userMessage: String? = null
) : PageableUIState<UiUser>(itemsPerPage = 8) {
    val users: List<UiUser>
        get() = pagedDataView()
}


class UsersViewModel(private val usersRepo: GithubUserRepository) : ViewModel(), PageableViewModel {

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
                data = dbUsers.map(DbUser::toUiUser),
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
                    data = apiData.map { user -> user.toUiUser() },
                    loadState = LoadState.LOADED
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    loadState = LoadState.LOADED,
                    userMessage = "Unable to load data from server, so cached data is dispalyed instead"
                )
            }
        }
    }

    fun clearSnackbarMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }

    override fun moveToNextPage() {
        val currentPage = _uiState.value.currentPage
        _uiState.update { it.copy(currentPage = currentPage + 1) }
    }

    override fun moveToPrevPage() {
        val currentPage = _uiState.value.currentPage
        _uiState.update { it.copy(currentPage = currentPage - 1) }
    }

}