package com.example.auth.data.mappers

import com.example.auth.data.dto.UserDto
import com.example.auth.domain.models.User

fun UserDto.toUser () : User {
    return User(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun User.toUserDto () : UserDto {
    return UserDto(
        id = id,
        username = username,
        email = email,
        firstName = firstName,
        lastName = lastName,
        gender = gender,
        image = image,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}