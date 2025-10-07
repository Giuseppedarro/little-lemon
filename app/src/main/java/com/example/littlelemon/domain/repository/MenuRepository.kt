package com.example.littlelemon.domain.repository

import com.example.littlelemon.domain.model.MenuItem


interface MenuRepository {
    suspend fun getMenuItems(): List<MenuItem>
}
