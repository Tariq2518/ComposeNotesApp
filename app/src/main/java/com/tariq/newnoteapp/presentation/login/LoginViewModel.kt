package com.tariq.newnoteapp.presentation.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tariq.newnoteapp.data.LoginUiState
import com.tariq.newnoteapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class LoginViewModel
@Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {


    val currentUser = repository.currentUser
    val userExists: Boolean
        get() = repository.userExist()

    var loginUiState by mutableStateOf(LoginUiState())
        private set


    fun onUserNameChanged(userName: String) {
        loginUiState = loginUiState.copy(userName = userName)
    }

    fun onPasswordChanged(userPassword: String) {
        loginUiState = loginUiState.copy(userPassword = userPassword)
    }

    fun onConfirmPasswordChange(confirmPasswordChange: String) {
        loginUiState = loginUiState.copy(confirmPassword = confirmPasswordChange)
    }

    fun onUserEmailChange(userEmail: String) {
        loginUiState = loginUiState.copy(userEmail = userEmail)
    }


    private fun validateLoginCredentials() =
        loginUiState.userEmail.isNotBlank() &&
                loginUiState.userPassword.isNotBlank()

    private fun validateSignUp() =
        loginUiState.userName.isNotBlank() &&
                loginUiState.userEmail.isNotBlank() &&
                loginUiState.userPassword.isNotBlank() &&
                loginUiState.confirmPassword.isNotBlank()


    fun createNewUser(context: Context) = viewModelScope.launch {

        try {
            if (!validateSignUp()) {
                throw IllegalArgumentException("Enter Correct Data")
            }
            loginUiState = loginUiState.copy(isLoading = true)
            if (loginUiState.userPassword != loginUiState.confirmPassword) {
                throw IllegalArgumentException("Password should be same")
            }

            loginUiState = loginUiState.copy(signUpError = null)

            repository?.createNewUser(loginUiState.userEmail, loginUiState.userPassword) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                    loginUiState.copy(isSuccess = true)
                } else {
                    Toast.makeText(context, "Failed to SignUp", Toast.LENGTH_SHORT).show()
                    loginUiState.copy(isSuccess = false)
                }
            }
        } catch (ex: Exception) {
            loginUiState = loginUiState.copy(signUpError = ex.localizedMessage)

        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }

    }

    fun signInUser(context: Context) = viewModelScope.launch {

        try {
            if (!validateLoginCredentials()) {
                throw IllegalArgumentException("Enter Correct Data")
            }
            loginUiState = loginUiState.copy(isLoading = true)

            loginUiState = loginUiState.copy(signInError = null)

            repository?.loginUser(loginUiState.userEmail, loginUiState.userPassword) { isSuccessful ->
                loginUiState = if (isSuccessful) {
                    Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
                    loginUiState.copy(isSuccess = true)
                } else {
                    Toast.makeText(context, "Failed to SignUp", Toast.LENGTH_SHORT).show()
                    loginUiState.copy(isSuccess = false)
                }
            }
        } catch (ex: Exception) {
            loginUiState = loginUiState.copy(signInError = ex.localizedMessage)

        } finally {
            loginUiState = loginUiState.copy(isLoading = false)
        }

    }

}