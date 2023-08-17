package com.tariq.newnoteapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tariq.newnoteapp.presentation.home.HomeScreen
import com.tariq.newnoteapp.presentation.login.LoginViewModel

sealed class MainNavRouts(val routes: String) {
    object Home : MainNavRouts("Home")
}


@Composable
fun MainNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel? = null
) {

    NavHost(
        navController = navController,
        route = Graph.MAIN_GRAPH,
        startDestination = MainNavRouts.Home.routes
    ) {

        composable(route = MainNavRouts.Home.routes) {
            HomeScreen(navController = navController, loginViewModel = loginViewModel)
        }


    }


}