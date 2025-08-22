package com.example.core.data.networking.dto

import kotlinx.serialization.Serializable

@Serializable
data class AccessTokenRequest(
    val refreshToken: String,
    val expiresInMins: Int = 60
)