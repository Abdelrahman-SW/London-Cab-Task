package com.example.core.domain.models

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: Int
)