package com.example.ghusers.ui.screens.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.api.model.ApiRepository
import com.example.ghusers.data.api.model.toUiRepository
import com.example.ghusers.data.db.entity.DbRepository
import com.example.ghusers.data.db.entity.toUiRepository
import com.example.ghusers.data.repo.GithubRepoRepository
import com.example.ghusers.ui.screens.util.LoadState
import com.example.ghusers.ui.uimodel.UiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReposUIState(
    val repos: List<UiRepository> = listOf(),
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
        loadDbRepos()
        loadApiRepos()
    }

    private fun loadDbRepos() {
        viewModelScope.launch {
            val dbData = githubRepoRepository.getAllCached(userLogin)
            _uiState.update {
                it.copy(
                    repos = dbData.map(DbRepository::toUiRepository),
                    loadState = LoadState.LOADED
                )
            }
        }
    }

    private fun loadApiRepos() {
        viewModelScope.launch {
            _uiState.update { it.copy(loadState = LoadState.LOADING_SERVER) }
            try {
                val apiData = githubRepoRepository.getAllApiRepos(userLogin)
                _uiState.update {
                    it.copy(
                        repos = apiData.map(ApiRepository::toUiRepository),
                        loadState = LoadState.LOADED
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loadState = LoadState.LOADED) }
            }
        }
    }

}