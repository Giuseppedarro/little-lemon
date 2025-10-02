package com.example.littlelemon.di

import com.example.littlelemon.data.repository.MenuRepositoryImpl
import com.example.littlelemon.domain.repository.MenuRepository
import com.example.littlelemon.domain.usecase.GetMenuUseCase
import com.example.littlelemon.ui.menu.MenuViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<MenuRepository> { MenuRepositoryImpl() }
    single { GetMenuUseCase(get()) }
    viewModel { MenuViewModel(get()) }
}
