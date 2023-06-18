package com.example.ghusers.ui.screens.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.api.model.ApiRepository
import com.example.ghusers.data.api.model.toUiRepository
import com.example.ghusers.data.repo.GithubRepoRepository
import com.example.ghusers.ui.uimodel.UiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReposUIState(
    val repos: List<UiRepository> = listOf()
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
        loadApiRepos()
    }

    private fun loadApiRepos() {
        viewModelScope.launch {
            val apiData = githubRepoRepository.getAllRepos(userLogin)
            _uiState.update {
                it.copy(repos = apiData.map(ApiRepository::toUiRepository))
            }
        }
    }
}