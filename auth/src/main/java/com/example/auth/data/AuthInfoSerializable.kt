package com.example.auth.data

import kotlinx.serialization.Serializable


@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val userId: Int
)