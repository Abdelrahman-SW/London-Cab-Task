package com.example.auth.data

import android.content.SharedPreferences
import com.example.auth.data.mappers.toAuthInfo
import com.example.auth.data.mappers.toAuthInfoSerializable
import com.example.auth.domain.AuthInfo
import com.example.auth.domain.AuthStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

class AuthStorageEncryptedSharedPrefs(
    val sharedPreferences: SharedPreferences
) : AuthStorage {
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val authInfoJson = sharedPreferences.getString(
                AUTH_INFO_KEY,
                ""
            )
            authInfoJson?.let {
                Json.decodeFromString<AuthInfoSerializable>(authInfoJson).toAuthInfo()
            }
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                sharedPreferences.edit().remove(AUTH_INFO_KEY).commit()
            } else {
                val authInfoJson = Json.encodeToString(info.toAuthInfoSerializable())
                sharedPreferences.edit().putString(AUTH_INFO_KEY, authInfoJson).commit()
            }
        }
    }

    companion object {
        val AUTH_INFO_KEY = "Auth_info"
    }
}