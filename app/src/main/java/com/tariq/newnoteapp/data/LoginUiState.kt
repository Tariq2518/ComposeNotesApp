package com.tariq.newnoteapp.data

data class LoginUiState(
    val userName:String = "",
    val userEmail:String = "",
    val userPassword:String = "",
    val confirmPassword:String = "",
    val isLoading:Boolean = false,
    val isSuccess:Boolean = false,
    val signUpError:String? = null,
    val signInError:String? = null,
)
