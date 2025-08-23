package com.example.londoncaptask.di

import android.content.SharedPreferences
import androidx.lifecycle.SavedStateHandle
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.auth.data.AuthRepositoryKtorImpl
import com.example.auth.domain.AuthRepository
import com.example.auth.presentation.LoginViewModel
import com.example.core.domain.AuthStorage
import com.example.tasks.presentation.tasks_list.TasksListViewModel
import com.example.core.data.auth.AuthStorageEncryptedSharedPrefsImpl
import com.example.core.data.networking.HttpClientFactory
import com.example.londoncaptask.MainViewModel
import com.example.londoncaptask.MyApp
import com.example.tasks.data.FetchTasksWorker
import com.example.tasks.data.OfflineFirstTaskRepository
import com.example.tasks.data.local.RoomLocalTaskDataSourceImpl
import com.example.tasks.data.local.db.TaskDb
import com.example.tasks.data.local.db.TasksDao
import com.example.tasks.data.remote.KtorRemoteDataSourceImpl
import com.example.tasks.domain.LocalTasksDataSource
import com.example.tasks.domain.RemoteTasksDataSource
import com.example.tasks.domain.TaskRepository
import com.example.tasks.presentation.upsert_tasks.UpsertTaskViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {


    // viewModels
    viewModelOf(::MainViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::TasksListViewModel)

    viewModel { (handle: SavedStateHandle) ->
        UpsertTaskViewModel(get(), handle)
    }

    singleOf(::AuthStorageEncryptedSharedPrefsImpl).bind<AuthStorage>()
    singleOf(::AuthRepositoryKtorImpl).bind<AuthRepository>()
    singleOf(::OfflineFirstTaskRepository).bind<TaskRepository>()
    singleOf(::RoomLocalTaskDataSourceImpl).bind<LocalTasksDataSource>()
    singleOf(::KtorRemoteDataSourceImpl).bind<RemoteTasksDataSource>()

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

    single<CoroutineScope> {
        (androidApplication() as MyApp).applicationScope
    }

    single<TaskDb> {
        Room.databaseBuilder(
            androidApplication(),
            TaskDb::class.java,
            "tasks.db"
        ).build()
    }

    single<TasksDao> {
        get<TaskDb>().taskDao()
    }

    workerOf(::FetchTasksWorker)
}