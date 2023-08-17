package com.tariq.newnoteapp.presentation.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tariq.newnoteapp.navigation.AuthNavRoutes
import com.tariq.newnoteapp.presentation.login.LoginViewModel

@Composable
fun Splash(
    navController: NavHostController,
    loginViewModel: LoginViewModel? = null
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(100.dp),
            imageVector = Icons.Default.Notifications,
            contentDescription = "",
        )

        navController.popBackStack()
        navController.navigate(route = AuthNavRoutes.Login.route)


    }

}