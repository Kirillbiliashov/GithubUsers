package com.example.ghusers.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ghusers.ui.screens.repos.ReposScreen
import com.example.ghusers.ui.screens.users.UsersScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.HOME,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destinations.HOME) {
            UsersScreen(onUserClick = { login ->
                navController.navigate(Destinations.getUserDestination(login))
            })
        }
        composable(
            route = Destinations.USER,
            arguments = listOf(navArgument("login") { type = NavType.StringType })
        ) {
            ReposScreen(onBackClick = { navController.popBackStack() })
        }
    }
}


object Destinations {
    val HOME = "Users"
    val USER = "User/{login}"
    fun getUserDestination(login: String) = "User/${login}"
}