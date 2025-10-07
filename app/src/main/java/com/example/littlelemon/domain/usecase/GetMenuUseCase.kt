package com.example.littlelemon.domain.usecase

import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.domain.repository.MenuRepository

class GetMenuUseCase(private val menuRepository: MenuRepository) {
    suspend operator fun invoke(): List<MenuItem> {
        return menuRepository.getMenuItems()
    }
}
