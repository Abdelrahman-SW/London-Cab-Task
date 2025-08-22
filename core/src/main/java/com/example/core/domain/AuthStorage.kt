package com.example.core.domain

import com.example.core.domain.models.AuthInfo

interface AuthStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}