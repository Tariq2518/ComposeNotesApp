package com.tariq.newnoteapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.input.key.Key.Companion.Home
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tariq.newnoteapp.presentation.home.HomeScreen
import com.tariq.newnoteapp.presentation.login.LoginViewModel

object Graph {
    const val ROOT_GRAPH = "root_graph"
    const val AUTH_GRAPH = "auth_graph"
    const val MAIN_GRAPH = "main_graph"
}

@Composable
fun RootNavGraph(navController: NavHostController, loginViewModel: LoginViewModel) {

    NavHost(
        navController = navController,
        startDestination = Graph.AUTH_GRAPH,
        route = Graph.ROOT_GRAPH
    ) {
        authNavGraph(navController, loginViewModel)
        composable(route = Graph.MAIN_GRAPH) {
            HomeScreen(navController = navController, loginViewModel = loginViewModel)
        }

    }
}