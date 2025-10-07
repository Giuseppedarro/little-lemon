package com.example.littlelemon.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.littlelemon.domain.model.MenuItem

@Entity(tableName = "menu_items")
data class MenuItemEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
) {
    fun toDomainModel() = MenuItem(
        name = title,
        description = description,
        price = price.toDouble(),
        image = image,
        category = category
    )
}
