package com.example.littlelemon.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.menu.MenuScreen
import com.example.littlelemon.ui.onboarding.OnboardingScreen
import com.example.littlelemon.ui.profile.ProfileScreen

// Define navigation routes
object Routes {
    const val Onboarding = "onboarding"
    const val Menu = "menu"
    const val Profile = "profile"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Onboarding) {
        composable(Routes.Onboarding) {
            OnboardingScreen()
        }
        composable(Routes.Menu) {
            MenuScreen()
        }
        composable(Routes.Profile) {
            ProfileScreen()
        }
    }
}
