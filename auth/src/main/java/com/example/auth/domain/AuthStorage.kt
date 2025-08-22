package com.example.auth.domain

interface AuthStorage {
    suspend fun get(): AuthInfo?
    suspend fun set(info: AuthInfo?)
}