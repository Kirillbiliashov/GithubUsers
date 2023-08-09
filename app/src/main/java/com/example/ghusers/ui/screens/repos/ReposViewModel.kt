package com.example.ghusers.ui.screens.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.model.Repository
import com.example.ghusers.data.repo.GithubRepoRepository
import com.example.ghusers.ui.screens.util.LoadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReposUIState(
    val repos: List<Repository> = listOf(),
    val loadState: LoadState = LoadState.LOADING_CACHE
)

class ReposViewModel(
    savedStateHandle: SavedStateHandle,
    private val githubRepoRepository: GithubRepoRepository
) : ViewModel() {

    private val userLogin: String = checkNotNull(savedStateHandle["login"])
    val topBarTitle = "$userLogin's repositories"

    private val _uiState = MutableStateFlow(ReposUIState())
    val uiState: StateFlow<ReposUIState> = _uiState

    init {
        viewModelScope.launch {
            loadDbRepos()
            loadApiRepos()
        }
    }

    private suspend fun loadDbRepos() {
        val dbData = githubRepoRepository.getAllCached(userLogin)
        _uiState.update {
            it.copy(
                repos = dbData,
                loadState = LoadState.LOADED
            )
        }
    }

    private suspend fun loadApiRepos() {
        _uiState.update { it.copy(loadState = LoadState.LOADING_SERVER) }
        try {
            val apiData = githubRepoRepository.getAllApiRepos(userLogin)
            _uiState.update {
                it.copy(
                    repos = apiData,
                    loadState = LoadState.LOADED
                )
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(loadState = LoadState.LOADED) }
        }
    }

}