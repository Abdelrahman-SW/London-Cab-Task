package com.example.auth.data

import com.example.auth.domain.repo.AuthRepository
import com.example.core.domain.LogoutAuth

class LogoutAuthImpl(
    private val authRepository: AuthRepository
) : LogoutAuth {
    override suspend fun logout() {
        authRepository.logout()
    }
}