package com.example.littlelemon.data.repository

import com.example.littlelemon.data.local.MenuDao
import com.example.littlelemon.data.remote.MenuService
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.domain.repository.MenuRepository

class MenuRepositoryImpl(
    private val menuService: MenuService,
    private val menuDao: MenuDao
) : MenuRepository {
    override suspend fun getMenuItems(): List<MenuItem> {
        if (menuDao.isEmpty()) {
            val menuItemsNetwork = menuService.getMenu().menu
            val menuItemsEntity = menuItemsNetwork.map { it.toEntity() }
            menuDao.insertAll(menuItemsEntity)
        }
        return menuDao.getAllMenuItems().map { it.toDomainModel() }
    }
}
