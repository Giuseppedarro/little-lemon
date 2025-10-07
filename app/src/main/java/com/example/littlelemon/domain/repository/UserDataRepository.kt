package com.example.littlelemon.domain.repository

interface UserDataRepository {
    fun saveUser(firstName: String, lastName: String, email: String)
    fun isUserRegistered(): Boolean
    fun getUserData(): Triple<String, String, String>
    fun clearUserData()
}
