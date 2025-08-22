package com.example.auth.data

import com.example.auth.data.dto.LoginRequestBody
import com.example.auth.data.dto.UserDto
import com.example.auth.data.mappers.toUser
import com.example.auth.domain.AuthRepository
import com.example.auth.domain.User
import com.example.core.data.networking.post
import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.core.domain.util.map
import io.ktor.client.HttpClient

class AuthRepositoryKtorImpl(
    private val client: HttpClient,
) : AuthRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Result<User, DataError.Network> {
        return client.post<LoginRequestBody, UserDto>(
            route = "/",
            body = LoginRequestBody(username, password)
        ).map { it.toUser() }
    }

    override suspend fun logout(): Result<Unit, DataError.Network> {
        // assume that we hit the server to logout and it respond with success
        return Result.Success(Unit)
    }

}