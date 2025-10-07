package com.example.littlelemon.data.repository

import com.example.littlelemon.data.local.MenuItemEntity
import com.example.littlelemon.data.remote.model.MenuItemNetwork
import com.example.littlelemon.domain.model.MenuItem

fun MenuItemNetwork.toEntity() = MenuItemEntity(
    id = id,
    title = title,
    description = description,
    price = price,
    image = image,
    category = category
)

fun MenuItemEntity.toDomainModel() = MenuItem(
    name = title,
    description = description,
    price = price.toDoubleOrNull() ?: 0.0,
    image = image,
    category = category
)
