package com.example.littlelemon.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MenuDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(menuItems: List<MenuItemEntity>)

    @Query("SELECT * FROM menu_items")
    suspend fun getAllMenuItems(): List<MenuItemEntity>

    @Query("SELECT (SELECT COUNT(*) FROM menu_items) == 0")
    suspend fun isEmpty(): Boolean

}
