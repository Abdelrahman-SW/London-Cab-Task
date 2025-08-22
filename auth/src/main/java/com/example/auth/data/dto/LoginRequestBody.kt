package com.example.auth.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestBody(
    val username : String,
    val password : String,
    val expiresInMins: Int = 60
)
