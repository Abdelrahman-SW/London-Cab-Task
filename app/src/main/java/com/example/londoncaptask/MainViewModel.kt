package com.example.londoncaptask

import android.opengl.Visibility
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.AuthStorage
import com.example.core.domain.DailyNotificationScheduler
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.hours

class MainViewModel(
    private val sessionStorage: AuthStorage,
    private val dailyNotificationScheduler: DailyNotificationScheduler
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {

        viewModelScope.launch {
            dailyNotificationScheduler.scheduleDailyNotification(2.hours)
        }

        viewModelScope.launch {
            state = state.copy(isCheckingAuth = true)
            state = state.copy(
                isLoggedIn = sessionStorage.get() != null
            )
            state = state.copy(isCheckingAuth = false)
        }
    }

    fun logout() {
        viewModelScope.launch {
            sessionStorage.set(null)
        }
    }

    fun setAnalyticsDialogVisibility (visibility: Boolean) {
        state = state.copy(showAnalyticsInstallDialog = visibility)
    }
}