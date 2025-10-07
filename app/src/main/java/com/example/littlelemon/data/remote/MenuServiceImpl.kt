package com.example.littlelemon.data.remote

import com.example.littlelemon.data.remote.model.MenuNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class MenuServiceImpl(
    private val httpClient: HttpClient
) : MenuService {

    companion object {
        private const val MENU_URL = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    }

    override suspend fun getMenu(): MenuNetwork {
        return httpClient.get(MENU_URL).body()
    }
}
