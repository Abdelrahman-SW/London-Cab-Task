package com.example.core.data.networking

import android.os.Build
import com.example.core.BuildConfig
import com.example.core.data.networking.dto.AccessTokenRequest
import com.example.core.data.networking.dto.AccessTokenResponse
import com.example.core.data.networking.ext.post
import com.example.core.domain.models.AuthInfo
import com.example.core.domain.AuthStorage
import com.example.core.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient

class HttpClientFactory(
    private val sessionStorage: AuthStorage,
) {

    // approach 1 for ssl pining : by using certificate pinner (hash of the public key of the certificate)

    val okHttpClient = OkHttpClient.Builder().apply {
        certificatePinner(
            CertificatePinner.Builder().add(
                BuildConfig.SERVER_DOMAIN, "sha256/${BuildConfig.SERVER_CERT_PIN}"
            ).build()
        )
    }.build()

    fun build(): HttpClient {
        return HttpClient(OkHttp) {

            install(HttpSend) {
                maxSendCount = 3 // retries
            }

            engine {
                preconfigured = okHttpClient
            }

            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
            install(Auth) {
                bearer {
                    loadTokens {
                        val info = sessionStorage.get()
                        BearerTokens(
                            accessToken = info?.accessToken ?: "",
                            refreshToken = info?.refreshToken ?: ""
                        )
                    }
                    refreshTokens {
                        val info = sessionStorage.get()
                        val response = client.post<AccessTokenRequest, AccessTokenResponse>(
                            route = "auth/refresh",
                            body = AccessTokenRequest(
                                refreshToken = info?.refreshToken ?: "",
                            )
                        )

                        if(response is Result.Success) {
                            val newAuthInfo = AuthInfo(
                                accessToken = response.data.accessToken,
                                refreshToken = info?.refreshToken ?: "",
                                userId = info?.userId ?: -1
                            )
                            sessionStorage.set(newAuthInfo)

                            BearerTokens(
                                accessToken = newAuthInfo.accessToken,
                                refreshToken = newAuthInfo.refreshToken
                            )
                        }
                        else {
                            BearerTokens(
                                accessToken = "",
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
        }
    }
}