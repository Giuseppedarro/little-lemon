package com.example.littlelemon.data.repository

import com.example.littlelemon.R
import com.example.littlelemon.domain.model.MenuItem
import com.example.littlelemon.domain.repository.MenuRepository

class MenuRepositoryImpl : MenuRepository {

    override suspend fun getMenuItems(): List<MenuItem> {
        return listOf(
            MenuItem(
                name = "Greek Salad",
                description = "The famous Greek salad of crispy lettuce, peppers, olives and our Chicago style feta cheese, garnished with crunchy garlic and rosemary croutons.",
                price = 12.99,
                image = R.drawable.ic_launcher_background
            ),
            MenuItem(
                name = "Bruschetta",
                description = "Our Bruschetta is made from grilled bread that has been smeared with garlic and seasoned with salt and olive oil.",
                price = 5.99,
                image = R.drawable.ic_launcher_background
            ),
            MenuItem(
                name = "Grilled Fish",
                description = "Fresh grilled fish, with a lemon-butter sauce.",
                price = 19.99,
                image = R.drawable.ic_launcher_background
            ),
            MenuItem(
                name = "Pasta",
                description = "Delicious pasta with a rich tomato sauce.",
                price = 14.99,
                image = R.drawable.ic_launcher_background
            )
        )
    }
}
