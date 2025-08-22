package com.example.core.data.auth.mappers

import com.example.core.data.auth.models.AuthInfoSerializable
import com.example.core.domain.models.AuthInfo

fun AuthInfoSerializable.toAuthInfo() : AuthInfo {
    return AuthInfo(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}

fun AuthInfo.toAuthInfoSerializable() : AuthInfoSerializable {
    return AuthInfoSerializable(
        accessToken = accessToken,
        refreshToken = refreshToken,
        userId = userId
    )
}