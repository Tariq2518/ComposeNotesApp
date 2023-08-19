package com.tariq.newnoteapp.presentation.screens.splash

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.tariq.newnoteapp.R
import com.tariq.newnoteapp.navigation.AuthNavRoutes
import com.tariq.newnoteapp.presentation.screens.login.LoginViewModel

@Composable
fun Splash(
    navController: NavHostController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier.size(width = 100.dp, height = 200.dp),
            painter = painterResource(id = R.drawable.ic_app_icon),
            contentDescription = "",
        )


        Handler(Looper.getMainLooper()).postDelayed({
            navController.popBackStack()
            navController.navigate(route = AuthNavRoutes.Login.route)
        }, 1000)


    }

}