package com.example.littlelemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.littlelemon.domain.repository.UserDataRepository
import com.example.littlelemon.navigation.AppNavigation
import com.example.littlelemon.navigation.Routes
import com.example.littlelemon.ui.theme.LittleLemonTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val userDataRepository: UserDataRepository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val startDestination = if (userDataRepository.isUserRegistered()) {
                        Routes.MENU
                    } else {
                        Routes.ONBOARDING
                    }
                    AppNavigation(startDestination = startDestination)
                }
            }
        }
    }
}
