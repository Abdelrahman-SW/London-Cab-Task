package com.example.auth.data

import com.example.auth.data.dto.LoginRequestBody
import com.example.auth.data.dto.UserDto
import com.example.auth.data.mappers.toUser
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.models.User
import com.example.core.data.networking.ext.post
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import io.ktor.client.HttpClient
import kotlinx.coroutines.delay

class AuthRepositoryKtorImpl(
    private val client: HttpClient,
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Result<User, DataError.Network> {
        return client.post<LoginRequestBody, UserDto>(
            route = "auth/login",
            body = LoginRequestBody(username, password)
        ).map { it.toUser() }
    }

    override suspend fun logout(): Result<Unit, DataError.Network> {
        // assume that we hit the server to logout and it respond with success
        delay(500L)
        return Result.Success(Unit)
    }

}