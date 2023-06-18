package com.example.ghusers.ui.screens.util

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoadingMessage(message: String,
                   modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = message, fontSize = 20.sp)
        Spacer(modifier = modifier.height(16.dp))
        CircularProgressIndicator()
    }
}

@Composable
fun PageBar(
    currPage: Int,
    onNextPageClick: () -> Unit,
    onPrevPageClick: () -> Unit,
    isLastPage: Boolean,
    isFirstPage: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Button(
            onClick = { if (!isFirstPage) onPrevPageClick() },
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .padding(horizontal = 4.dp)
                .alpha(if (!isFirstPage) 1f else 0f)
        ) {
            Text(text = "<<")
        }
        Button(
            onClick = { if (!isFirstPage) onPrevPageClick() },
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .padding(horizontal = 4.dp)
                .alpha(if (!isFirstPage) 1f else 0f)
        ) {
            Text(text = (currPage - 1).toString())
        }
        Button(
            onClick = { },
            shape = RoundedCornerShape(16.dp),
            modifier = modifier.padding(horizontal = 4.dp)
        ) {
            Text(text = currPage.toString())
        }
        Button(
            onClick = { if (!isLastPage) onNextPageClick() },
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .padding(horizontal = 4.dp)
                .alpha(if (!isLastPage) 1f else 0f)
        ) {
            Text(text = (currPage + 1).toString())
        }
        Button(
            onClick = { if (!isLastPage) onNextPageClick() },
            shape = RoundedCornerShape(16.dp),
            modifier = modifier
                .padding(horizontal = 4.dp)
                .alpha(if (!isLastPage) 1f else 0f)
        ) {
            Text(text = ">>")
        }
    }
}