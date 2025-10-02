package com.example.littlelemon.domain.model

import androidx.annotation.DrawableRes

data class MenuItem(
    val name: String,
    val description: String,
    val price: Double,
    @DrawableRes val image: Int
)
