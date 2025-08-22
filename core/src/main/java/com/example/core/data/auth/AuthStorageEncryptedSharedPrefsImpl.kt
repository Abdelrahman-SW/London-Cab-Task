package com.example.core.data.auth

import android.content.SharedPreferences
import com.example.core.data.auth.mappers.toAuthInfo
import com.example.core.data.auth.mappers.toAuthInfoSerializable
import com.example.core.data.auth.models.AuthInfoSerializable
import com.example.core.domain.models.AuthInfo
import com.example.core.domain.AuthStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import androidx.core.content.edit

class AuthStorageEncryptedSharedPrefsImpl(
    val sharedPreferences: SharedPreferences
) : AuthStorage {
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val authInfoJson = sharedPreferences.getString(
                AUTH_INFO_KEY,
                null
            )
            authInfoJson?.let {
                Json.decodeFromString<AuthInfoSerializable>(authInfoJson).toAuthInfo()
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                sharedPreferences.edit(commit = true) { remove(AUTH_INFO_KEY) }
            } else {
                val authInfoJson = Json.encodeToString<AuthInfoSerializable>(info.toAuthInfoSerializable())
                sharedPreferences.edit(commit = true) { putString(AUTH_INFO_KEY, authInfoJson) }
            }
        }
    }

    companion object {
        const val AUTH_INFO_KEY = "Auth_info"
    }
}