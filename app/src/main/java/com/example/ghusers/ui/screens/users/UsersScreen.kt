package com.example.ghusers.ui.screens.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ghusers.data.model.ApiUser
import com.example.ghusers.ui.AppViewModelProvider
import com.example.ghusers.ui.navigation.Destinations
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: UsersViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text(text = Destinations.HOME) }) }) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (uiState.value.loadState) {
                LoadState.LOADING -> LoadingMessage()
                LoadState.ERROR -> ErrorMessage(onButtonClick = viewModel::loadGithubUsersFromApi)
                LoadState.LOADED -> UsersList(
                    users = uiState.value.apiUsers,
                    onUserClick = onUserClick
                )
            }
        }
    }
}

@Composable
fun LoadingMessage(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Loading users", fontSize = 20.sp)
    }
}

@Composable
fun ErrorMessage(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Network connection issue. Check your network connection and try again")
        Spacer(modifier = modifier.width(24.dp))
        Button(onClick = onButtonClick) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun UsersList(
    users: List<ApiUser>,
    onUserClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(items = users, key = { it.id }) {
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
