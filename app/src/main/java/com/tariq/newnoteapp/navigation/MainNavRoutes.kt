package com.tariq.newnoteapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.tariq.newnoteapp.presentation.screens.home.HomeScreen
import com.tariq.newnoteapp.presentation.screens.login.LoginViewModel
import com.tariq.newnoteapp.presentation.screens.note_screen.NotesScreen

sealed class MainNavRouts(val routes: String) {
    object Home : MainNavRouts("Home")
    object NotesScreen : MainNavRouts("Notes?{noteId}") {
        fun passNoteId(noteId: String): String {
            return "Notes?$noteId"
        }
    }
}

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {
    navigation(
        route = Graph.MAIN_GRAPH,
        startDestination = MainNavRouts.Home.routes
    ) {
        composable(route = MainNavRouts.Home.routes) {
            HomeScreen(navController = navController)
        }

        composable(
            route = MainNavRouts.NotesScreen.routes,
            arguments = listOf(navArgument("noteId") {
                type = NavType.StringType
            })
        ) {
            NotesScreen(navController = navController)
        }


    }
}

@Composable
fun MainNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        route = Graph.MAIN_GRAPH,
        startDestination = MainNavRouts.Home.routes
    ) {

        composable(route = MainNavRouts.Home.routes) {
            HomeScreen(navController = navController)
        }

        composable(
            route = MainNavRouts.NotesScreen.routes,
            arguments = listOf(navArgument("noteId") {
                type = NavType.StringType
            })
        ) {
            NotesScreen(navController = navController)
        }
    }


}

