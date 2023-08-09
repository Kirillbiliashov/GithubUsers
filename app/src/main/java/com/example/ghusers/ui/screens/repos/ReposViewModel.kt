package com.example.ghusers.ui.screens.repos

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghusers.data.model.Repository
import com.example.ghusers.data.repo.GithubRepoRepository
import com.example.ghusers.ui.screens.util.LoadState
import com.example.ghusers.ui.screens.util.PageableUIState
import com.example.ghusers.ui.screens.util.PageableViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val REPOS_PER_PAGE = 10

data class ReposUIState(
    override val data: List<Repository> = listOf(),
    val loadState: LoadState = LoadState.LOADING_CACHE,
    override val currentPage: Int = 1
) : PageableUIState<Repository>() {
    val repos: List<Repository>
        get() = pagedDataView()
}


class ReposViewModel(
    savedStateHandle: SavedStateHandle,
    private val githubRepoRepository: GithubRepoRepository
) : ViewModel(), PageableViewModel {

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
                data = dbData,
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
                    data = apiData,
                    loadState = LoadState.LOADED
                )
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(loadState = LoadState.LOADED) }
        }
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