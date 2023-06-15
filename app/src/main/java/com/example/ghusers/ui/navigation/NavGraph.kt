package com.example.ghusers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ghusers.ui.screens.repos.ReposScreen
import com.example.ghusers.ui.screens.users.UsersScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Destinations.HOME,
    modifier: Modifier = Modifier,

    ) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Destinations.HOME) {
            UsersScreen(onUserClick = {
                navController.navigate(Destinations.getUserDestination(it))
            })
        }
        composable(
            route = Destinations.USER,
            arguments = listOf(navArgument("login") { type = NavType.StringType })
        ) {
            ReposScreen()
        }
    }

}


object Destinations {
    val HOME = "users"
    val USER = "user/{login}"
    fun getUserDestination(login: String) = "user/${login}"
}