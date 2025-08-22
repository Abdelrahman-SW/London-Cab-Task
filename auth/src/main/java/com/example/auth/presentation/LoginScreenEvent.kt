package com.example.auth.presentation

import com.example.core.presentation.ui.UiText

sealed interface LoginScreenEvent {
    data object LoggedIn : LoginScreenEvent
    data class Error (val error : UiText) : LoginScreenEvent
}