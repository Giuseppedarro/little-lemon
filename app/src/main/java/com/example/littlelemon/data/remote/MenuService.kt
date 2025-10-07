package com.example.littlelemon.data.remote

import com.example.littlelemon.data.remote.model.MenuNetwork

interface MenuService {
    suspend fun getMenu(): MenuNetwork
}
