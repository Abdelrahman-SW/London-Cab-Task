package com.example.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.core.presentation.util.ObserveAsEvents
import com.example.core.presentation.util.showToast
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

}


@Preview
@Composable
private fun LoginScreenPreview() {
    LoginScreen(
        loginScreenState = LoginScreenState(),
        onAction = {},
    )
}