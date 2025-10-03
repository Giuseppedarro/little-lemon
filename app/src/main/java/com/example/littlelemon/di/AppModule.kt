package com.example.littlelemon.di

import com.example.littlelemon.data.UserDataRepository
import com.example.littlelemon.data.UserDataRepositoryImpl
import com.example.littlelemon.data.repository.MenuRepositoryImpl
import com.example.littlelemon.domain.repository.MenuRepository
import com.example.littlelemon.domain.usecase.GetMenuUseCase
import com.example.littlelemon.ui.menu.MenuViewModel
import com.example.littlelemon.ui.onboarding.OnboardingViewModel
import com.example.littlelemon.ui.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<MenuRepository> { MenuRepositoryImpl() }
    single { GetMenuUseCase(get()) }
    viewModel { MenuViewModel(get()) }
    single<UserDataRepository> { UserDataRepositoryImpl(androidContext()) }
    viewModel { OnboardingViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}
