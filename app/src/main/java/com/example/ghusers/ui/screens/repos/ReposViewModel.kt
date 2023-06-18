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
import com.example.ghusers.ui.screens.util.PageableUIState
import com.example.ghusers.ui.screens.util.PageableViewModel
import com.example.ghusers.ui.uimodel.UiRepository
import com.example.ghusers.ui.uimodel.UiUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.min

private const val REPOS_PER_PAGE = 10

data class ReposUIState(
    override val data: List<UiRepository> = listOf(),
    val loadState: LoadState = LoadState.LOADING_CACHE,
    override val currentPage: Int = 1
) : PageableUIState<UiRepository>() {
    val repos: List<UiRepository>
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
        loadDbRepos()
        loadApiRepos()
    }

    private fun loadDbRepos() {
        viewModelScope.launch {
            val dbData = githubRepoRepository.getAllCached(userLogin)
            _uiState.update {
                it.copy(
                    data = dbData.map(DbRepository::toUiRepository),
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
                        data = apiData.map(ApiRepository::toUiRepository),
                        loadState = LoadState.LOADED
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(loadState = LoadState.LOADED) }
            }
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