package com.example.auth.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.auth.domain.AuthInfo
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.AuthStorage
import com.example.auth.domain.User
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.presentation.ui.asUiText
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val authStorage: AuthStorage
) : ViewModel() {

    var state by mutableStateOf(LoginScreenState())
        private set

    private val _events = Channel<LoginScreenEvent>()
    val events = _events.receiveAsFlow()


    fun onAction (event: LoginScreenAction) {
        when(event) {
            LoginScreenAction.OnLoginClicked -> {
                loginUser()
            }
            is LoginScreenAction.OnPasswordChanged -> {
                state = state.copy(
                    passwordTextField = event.password
                )
            }
            is LoginScreenAction.OnUsernameChanged -> {
                state = state.copy(
                    usernameTextField = event.username
                )
            }
        }
    }

    private fun loginUser() {
        state = state.copy(
            isLogging = true
        )
        viewModelScope.launch {
            val result = authRepository.login(
                state.usernameTextField ,
                state.passwordTextField
            )
            when (result) {
                is Result.Error<DataError.Network> -> {
                    _events.trySend(LoginScreenEvent.Error(result.error.asUiText()))
                }
                is Result.Success<User> -> {
                      // here we need to save both access token and refresh token
                      authStorage.set(
                          AuthInfo(
                              accessToken = result.data.accessToken,
                              refreshToken = result.data.refreshToken,
                              userId = result.data.id
                          )
                      )
                     _events.trySend(LoginScreenEvent.LoggedIn)
                }
            }
        }
    }

}