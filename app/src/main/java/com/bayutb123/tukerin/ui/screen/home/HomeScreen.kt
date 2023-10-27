package com.bayutb123.tukerin.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.screen.home.dashboard.DashboardScreen
import com.bayutb123.tukerin.ui.screen.home.profile.ProfileScreen
import com.bayutb123.tukerin.ui.screen.home.saved.SavedScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit,
    onLogoutRequested: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()
    NavHost(navController = homeNavController, startDestination = Screen.Dashboard.route) {
        composable(route = Screen.Dashboard.route) {
            DashboardScreen(
                onLogoutRequested = {
                    onLogoutRequested(it)
                },
                onNavigationRequested = {
                    onNavigationRequested(it)
                }
            )
        }
        composable(route = Screen.Saved.route) {
            SavedScreen()
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen()
        }
    }
}