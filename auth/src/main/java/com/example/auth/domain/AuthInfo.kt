package com.example.auth.domain

data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val userId: Int
)