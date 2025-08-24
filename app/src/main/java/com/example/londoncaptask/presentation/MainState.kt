package com.example.londoncaptask.presentation

data class MainState(
    val isLoggedIn: Boolean = false,
    val isCheckingAuth: Boolean = false,
    val showAnalyticsInstallDialog : Boolean = false,
)