package com.example.auth.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

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
        onAction = {}
    )
}