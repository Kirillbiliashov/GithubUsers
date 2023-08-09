package com.example.ghusers.ui.screens.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ghusers.data.model.User
import com.example.ghusers.ui.AppViewModelProvider
import com.example.ghusers.ui.navigation.Destinations
import com.example.ghusers.ui.screens.util.LoadState
import com.example.ghusers.ui.screens.util.LoadingMessage
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val viewModel: UsersViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopAppBar(title = { Text(text = Destinations.HOME) }) }) {
        uiState.value.userMessage?.let {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(it, duration = SnackbarDuration.Short)
                viewModel.clearSnackbarMessage()
            }
        }
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (uiState.value.loadState) {
                LoadState.LOADING_CACHE -> LoadingMessage("Loading users from cache")
                LoadState.LOADING_SERVER -> LoadingMessage("Loading users from server")
                LoadState.LOADED -> UsersList(
                    users = uiState.value.users,
                    onUserClick = onUserClick
                )
            }
        }
    }
}

@Composable
fun UsersList(
    users: List<User>,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberLazyListState()
    LazyColumn(state = state) {
        items(items = users, key = { it.login }) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(72.dp)
                    .clickable { onUserClick(it.login) }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.width(8.dp))
                    GlideImage(
                        imageModel = { it.avatarUrl },
                        imageOptions =
                        ImageOptions(contentScale = ContentScale.Crop),
                        modifier = modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(50.dp))
                    )
                    Spacer(modifier = modifier.width(16.dp))
                    Text(text = it.login, fontSize = 20.sp, fontWeight = FontWeight.W500)
                }
            }

        }
    }
}