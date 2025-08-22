package com.example.auth.data.mappers

import com.example.auth.data.AuthInfoSerializable
import com.example.auth.domain.AuthInfo

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