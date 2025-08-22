package com.example.auth.presentation

data class LoginScreenState(
    val isLogging : Boolean = false,
    val usernameTextField : String = "" ,
    val passwordTextField : String = "" ,

)
