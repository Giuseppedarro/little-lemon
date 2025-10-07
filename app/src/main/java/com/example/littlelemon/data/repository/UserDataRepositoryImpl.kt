package com.example.littlelemon.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.littlelemon.domain.repository.UserDataRepository

class UserDataRepositoryImpl(context: Context) : UserDataRepository {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    companion object {
        const val KEY_FIRST_NAME = "first_name"
        const val KEY_LAST_NAME = "last_name"
        const val KEY_EMAIL = "email"
        const val KEY_IS_REGISTERED = "is_registered"
    }

    override fun saveUser(firstName: String, lastName: String, email: String) {
        with(sharedPreferences.edit()) {
            putString(KEY_FIRST_NAME, firstName)
            putString(KEY_LAST_NAME, lastName)
            putString(KEY_EMAIL, email)
            putBoolean(KEY_IS_REGISTERED, true)
            apply()
        }
    }

    override fun isUserRegistered(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_REGISTERED, false)
    }

    override fun getUserData(): Triple<String, String, String> {
        val firstName = sharedPreferences.getString(KEY_FIRST_NAME, "") ?: ""
        val lastName = sharedPreferences.getString(KEY_LAST_NAME, "") ?: ""
        val email = sharedPreferences.getString(KEY_EMAIL, "") ?: ""
        return Triple(firstName, lastName, email)
    }

    override fun clearUserData() {
        with(sharedPreferences.edit()) {
            clear()
            apply()
        }
    }
}