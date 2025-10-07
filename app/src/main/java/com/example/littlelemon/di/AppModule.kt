package com.example.littlelemon.di

import android.app.Application
import androidx.room.Room
import com.example.littlelemon.data.repository.UserDataRepositoryImpl
import com.example.littlelemon.data.local.AppDatabase
import com.example.littlelemon.data.local.MenuDao
import com.example.littlelemon.data.remote.MenuService
import com.example.littlelemon.data.remote.MenuServiceImpl
import com.example.littlelemon.data.repository.MenuRepositoryImpl
import com.example.littlelemon.domain.repository.MenuRepository
import com.example.littlelemon.domain.repository.UserDataRepository
import com.example.littlelemon.domain.usecase.GetMenuUseCase
import com.example.littlelemon.ui.menu.MenuViewModel
import com.example.littlelemon.ui.onboarding.OnboardingViewModel
import com.example.littlelemon.ui.profile.ProfileViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<HttpClient> {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    },
                    contentType = ContentType.Text.Plain
                )
            }
        }
    }

    single<MenuService> {
        MenuServiceImpl(get())
    }

    single<AppDatabase> {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "little_lemon_db"
        ).build()
    }

    single<MenuDao> {
        get<AppDatabase>().menuDao()
    }

    single<MenuRepository> {
        MenuRepositoryImpl(get(), get())
    }

    single<UserDataRepository> {
        UserDataRepositoryImpl(androidContext())
    }

    factory { GetMenuUseCase(get()) }

    viewModel { MenuViewModel(get()) }
    viewModel { OnboardingViewModel(get()) }
    viewModel { ProfileViewModel(get()) }

}
