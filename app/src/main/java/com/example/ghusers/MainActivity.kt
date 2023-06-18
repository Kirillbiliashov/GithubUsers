package com.example.ghusers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.ghusers.data.api.model.toDBRepository
import com.example.ghusers.data.api.model.toDBUser
import com.example.ghusers.ui.navigation.NavGraph
import com.example.ghusers.ui.theme.GHUsersTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GHUsersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        val app = this.application as GithubUsersApplication
        val userRepository = app.container.githubUserRepository
        val repoRepository = app.container.githubRepoRepository
        lifecycleScope.launch {
            val apiUsers = try {
                userRepository.getAllUsers()
            } catch (e: Exception) {
                listOf()
            }
            if (apiUsers.isNotEmpty()) {
                val dbUsers = apiUsers.map { it.toDBUser() }
                userRepository.refreshUserCache(dbUsers)
                val dbRepos = apiUsers
                    .map { async { repoRepository.getAllApiRepos(it.login) } }
                    .awaitAll()
                    .flatten()
                    .map { it.toDBRepository() }
                repoRepository.refreshReposCache(dbRepos)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GHUsersTheme {

    }
}