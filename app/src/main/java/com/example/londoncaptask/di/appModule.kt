package com.example.londoncaptask.di

import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.auth.data.AuthRepositoryKtorImpl
import com.example.auth.domain.AuthRepository
import com.example.core.domain.AuthStorage
import com.example.auth.presentation.LoginViewModel
import com.example.core.data.auth.AuthStorageEncryptedSharedPrefsImpl
import com.example.core.data.networking.HttpClientFactory
import com.example.londoncaptask.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {


    // viewModels
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)

    singleOf(::AuthStorageEncryptedSharedPrefsImpl).bind<AuthStorage>()
    singleOf(::AuthRepositoryKtorImpl).bind<AuthRepository>()

    singleOf(::HttpClientFactory)

    // http client of ktor
    single {
        get<HttpClientFactory>().build()
    }

    single<SharedPreferences> {
        val masterKey = MasterKey.Builder(androidApplication())
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            androidApplication(),
            "auth_pref",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }


}