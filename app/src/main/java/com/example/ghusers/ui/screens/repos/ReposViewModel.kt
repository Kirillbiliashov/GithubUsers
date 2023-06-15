package com.example.ghusers.ui.screens.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.model.Repository
import com.example.ghusers.data.model.User
import com.example.ghusers.data.repo.GithubRepoRepository
import com.example.ghusers.ui.screens.users.UsersUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReposUIState(
    val repos: List<Repository> = listOf()
)

class ReposViewModel(
    savedStateHandle: SavedStateHandle,
    private val githubRepoRepository: GithubRepoRepository
) : ViewModel() {

    private val userLogin: String = checkNotNull(savedStateHandle["login"])

    private val _uiState = MutableStateFlow(ReposUIState())
    val uiState: StateFlow<ReposUIState> = _uiState

    init {
        println("user login: $userLogin")
        loadApiRepos()
    }

    private fun loadApiRepos() {
        viewModelScope.launch {
            val apiData = githubRepoRepository.getAllRepos(userLogin)
            _uiState.update { it.copy(repos = apiData) }
        }
    }
}