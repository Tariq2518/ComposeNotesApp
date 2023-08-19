package com.tariq.newnoteapp.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.tariq.newnoteapp.navigation.AuthNavRoutes
import com.tariq.newnoteapp.navigation.MainNavRouts
import com.tariq.newnoteapp.ui.theme.NewNotesAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavHostController,
    loginViewModel: LoginViewModel? = hiltViewModel(),
    onNavigateToHome: () -> Unit = {},
    onNavigateToSignIn: () -> Unit = { },
) {
    val loginUiState = loginViewModel?.loginUiState
    val isSignError = loginUiState?.signUpError != null
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.size(40.dp))
        Text(
            text = AnnotatedString("Sign Up"),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Black
        )
        Spacer(modifier = Modifier.size(40.dp))
        if (isSignError) {
            Text(
                text = loginUiState?.signUpError ?: "Unknown Error",
                color = Color.Red
            )
        }
        Spacer(modifier = Modifier.size(20.dp))
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            value = loginUiState?.userName ?: "",
            onValueChange = {
                loginViewModel?.onUserNameChanged(it)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = "email")
            },
            label = {
                Text(text = "Username")
            },
            isError = isSignError,
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
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
            isError = isSignError,
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
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
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            value = loginUiState?.confirmPassword ?: "",
            onValueChange = {
                loginViewModel?.onConfirmPasswordChange(it)
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "email")
            },
            label = {
                Text(text = "Confirm Password")
            },
            isError = isSignError,
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(20),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
        Spacer(modifier = Modifier.size(20.dp))

        Button(
            onClick = { loginViewModel?.createNewUser(context) }) {
            Text(text = "Sign In", modifier = Modifier.padding(horizontal = 40.dp, vertical = 6.dp))
        }

        Spacer(modifier = Modifier.size(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Already have an Account?")
            Spacer(modifier = Modifier.size(10.dp))
            TextButton(onClick = {
                navController.popBackStack()
                navController.navigate(route = AuthNavRoutes.Login.route)
            }) {
                Text(text = "Sign In")
            }

        }

        if (loginUiState?.isLoading == true) {
            CircularProgressIndicator()
        }

        LaunchedEffect(key1 = loginViewModel?.userExists) {
            if (loginViewModel?.userExists == true) {
                navController.popBackStack()
                navController.navigate(MainNavRouts.Home.routes)
            }
        }

    }

}


@Preview
@Composable
fun SignUpScreenPreview(

) {
    NewNotesAppTheme(darkTheme = false) {
        SignUpScreen(rememberNavController()) {

        }
    }

}