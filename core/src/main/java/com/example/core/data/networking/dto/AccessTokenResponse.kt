package com.example.core.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenResponse(
    val accessToken: String,
    val refreshToken: String
)