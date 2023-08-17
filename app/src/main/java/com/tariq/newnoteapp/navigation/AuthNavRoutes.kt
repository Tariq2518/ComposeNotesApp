package com.tariq.newnoteapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tariq.newnoteapp.presentation.login.LoginScreen
import com.tariq.newnoteapp.presentation.login.LoginViewModel
import com.tariq.newnoteapp.presentation.login.SignUpScreen
import com.tariq.newnoteapp.presentation.splash.Splash


sealed class AuthNavRoutes(val route: String) {
    object Splash : AuthNavRoutes("Splash")
    object Login : AuthNavRoutes("Login")
    object SignUp : AuthNavRoutes("SignUp")
}


@RequiresApi(Build.VERSION_CODES.N)
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel? = null
) {

    navigation(
        route = Graph.AUTH_GRAPH, startDestination = AuthNavRoutes.Splash.route
    ) {
        composable(
            route = AuthNavRoutes.Splash.route
        ) {
            Splash(
                navController = navController, loginViewModel = loginViewModel
            )
        }

        composable(route = AuthNavRoutes.SignUp.route) {
            SignUpScreen(
                navController = navController, loginViewModel = loginViewModel
            )
        }

        composable(route = AuthNavRoutes.Login.route) {
            LoginScreen(
                navController = navController, loginViewModel = loginViewModel
            )
        }


    }

}