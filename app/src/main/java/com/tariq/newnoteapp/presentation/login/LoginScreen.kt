package com.tariq.newnoteapp.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tariq.newnoteapp.navigation.AuthNavRoutes
import com.tariq.newnoteapp.navigation.Graph
import com.tariq.newnoteapp.ui.theme.NewNotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel? = null,
    onNavigateToHome: () -> Unit = { },
    onNavigateToSignUp: () -> Unit = { },
) {
    val loginUiState = loginViewModel?.loginUiState
    val isSignError = loginUiState?.signInError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.size(40.dp))
        Text(
            text = AnnotatedString("Login"),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.size(40.dp))
        if (isSignError) {
            Text(
                text = loginUiState?.signInError ?: "Unknown Error",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userEmail ?: "",
            onValueChange = {
                loginViewModel?.onUserEmailChange(it)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "email")
            },
            label = {
                Text(text = "Email")
            },
            isError = isSignError
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            value = loginUiState?.userPassword ?: "",
            onValueChange = {
                loginViewModel?.onPasswordChanged(it)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "email")
            },
            label = {
                Text(text = "Password")
            },
            isError = isSignError,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = { loginViewModel?.signInUser(context) }) {
            Text(
                text = "Sign In",
                modifier = Modifier.padding(horizontal = 40.dp, vertical = 6.dp)
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Don't have an Account?")
            Spacer(modifier = Modifier.size(10.dp))
            TextButton(onClick = {
                // onNavigateToSignUp.invoke()
                navController.popBackStack()
                navController.navigate(route = AuthNavRoutes.SignUp.route)
            }) {
                Text(text = "Sign Up")
            }

        }

        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.userExists) {
            if (loginViewModel?.userExists == true) {
                //onNavigateToHome.invoke()

                navController.popBackStack()
                navController.navigate(Graph.MAIN_GRAPH)
            }
        }

    }

}


@Preview
@Composable
fun LoginPreview(

) {
    NewNotesAppTheme(darkTheme = false) {
        LoginScreen(rememberNavController())
    }

}