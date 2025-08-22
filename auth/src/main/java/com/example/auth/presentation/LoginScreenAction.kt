package com.example.auth.presentation

sealed interface LoginScreenAction {
    data class OnUsernameChanged (val username : String) : LoginScreenAction
    data class OnPasswordChanged (val password : String): LoginScreenAction
    data object OnLoginClicked: LoginScreenAction
}