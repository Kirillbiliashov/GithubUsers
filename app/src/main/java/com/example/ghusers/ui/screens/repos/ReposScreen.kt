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
import androidx.compose.material3.Card
import androidx.compose.material3.Text
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
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ReposScreen(modifier: Modifier = Modifier) {
    val viewModel: ReposViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
    val uiState = viewModel.uiState.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(items = uiState.value.repos, key = { it.name }) {
                val parsedDate = remember(it.createdAt) {
                    it.createdAt.toFormattedString()
                }
                val descText = remember(it.description) {
                    it.description ?: "No description provided."
                }
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
                            modifier = modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = it.name,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.W600,
                                modifier = modifier.fillMaxWidth(0.6F)
                            )
                            Column(modifier = modifier.fillMaxHeight()) {
                                Text(text = if (it.private) "Private" else "Public")
                                Text(text = "Created: $parsedDate")
                            }
                        }
                        Text(text = descText, color = Color(111, 109, 109, 255))
                    }
                }
            }
        }
    }
}

private fun Date.toFormattedString(): String {
    val dateFormat = SimpleDateFormat("dd.MM.yy")
    return dateFormat.format(this)
}