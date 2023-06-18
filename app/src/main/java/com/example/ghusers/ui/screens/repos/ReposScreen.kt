package com.example.ghusers.ui.screens.repos

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ghusers.ui.AppViewModelProvider
import com.example.ghusers.ui.screens.util.LoadState
import com.example.ghusers.ui.screens.util.LoadingMessage
import com.example.ghusers.ui.uimodel.UiRepository
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReposScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: ReposViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = viewModel.topBarTitle) },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }
        )
    }) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (uiState.value.loadState) {
                LoadState.LOADING_CACHE -> LoadingMessage("Loading repos from cache")
                LoadState.LOADING_SERVER -> LoadingMessage("Loading repos from server")
                LoadState.LOADED -> ReposList(repos = uiState.value.repos)
            }
        }
    }
}

@Composable
fun ReposList(repos: List<UiRepository>, modifier: Modifier = Modifier) {
    LazyColumn {
        items(items = repos, key = { it.name }) { repo ->
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .heightIn(min = 72.dp)
            ) {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = repo.name,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W600,
                            modifier = Modifier.fillMaxWidth(0.6F)
                        )
                        Column(modifier = Modifier.fillMaxHeight()) {
                            Text(text = if (repo.private) "Private" else "Public")
                            Text(text = "Created: ${repo.dateStr}")
                        }
                    }
                    Text(
                        text = repo.descriptionText,
                        color = Color(111, 109, 109, 255)
                    )
                }
            }
        }
    }
}