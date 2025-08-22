package com.example.auth.domain

import com.example.auth.domain.models.User
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result

interface AuthRepository {
    suspend fun login(
        username: String,
        password: String
    ): Result<User, DataError.Network>

    suspend fun logout(): Result<Unit, DataError.Network>
}