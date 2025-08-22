package com.example.auth.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.util.ObserveAsEvents
import com.example.core.presentation.util.ext.showToast
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    onUserLoggedIn: () -> Unit,
    loginViewModel: LoginViewModel = koinViewModel()
) {
    val context = LocalContext.current
    ObserveAsEvents(loginViewModel.events) {
        when (it) {
            is LoginScreenEvent.Error -> {
                context.showToast(it.error)
            }

            LoginScreenEvent.LoggedIn -> {
                onUserLoggedIn.invoke()
            }
        }
    }
    LoginScreen(
        modifier = modifier,
        loginScreenState = loginViewModel.state,
        onAction = loginViewModel::onAction,
    )
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginScreenState: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit
) {
    Box (

    ) {
        Column(
            modifier = Modifier.padding(all = 24.dp)
        ) {
            TextField(
                label = {
                    Text(
                        text = "username"
                    )
                },
                value = loginScreenState.usernameTextField,
                onValueChange = {
                    onAction(LoginScreenAction.OnUsernameChanged(it))
                }
            )

            Spacer(Modifier.height(32.dp))

            TextField(
                label = {
                    Text(
                        text = "password"
                    )
                },
                value = loginScreenState.passwordTextField,
                onValueChange = {
                    onAction(LoginScreenAction.OnPasswordChanged(it))
                }
            )

            Spacer(Modifier.weight(1f))

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onAction(LoginScreenAction.OnLoginClicked)
                }
            ) {
                Text("Login")
            }
        }

        if (loginScreenState.isLogging) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        loginScreenState = LoginScreenState(),
        onAction = {},
    )
}