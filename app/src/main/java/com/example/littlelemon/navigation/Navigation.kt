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
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.Onboarding) {
            OnboardingScreen(
                onNavigateToMenu = {
                    navController.navigate(Routes.Menu) {
                        popUpTo(Routes.Onboarding) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Routes.Menu) {
            MenuScreen(
                onNavigateToProfile = { navController.navigate(Routes.Profile) }
            )
        }
        composable(Routes.Profile) {
            ProfileScreen(
                onNavigateToOnboarding = {
                    navController.navigate(Routes.Onboarding) {
                        popUpTo(Routes.Menu) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}
