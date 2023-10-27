package com.bayutb123.tukerin.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.screen.auth.login.LoginScreen
import com.bayutb123.tukerin.ui.screen.auth.register.RegisterScreen
import com.bayutb123.tukerin.ui.screen.detail.DetailScreen
import com.bayutb123.tukerin.ui.screen.home.HomeScreen

@Composable
fun App(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Home.route ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onLogoutRequested = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen {
                navController.navigate(it)
            }
        }
        composable(route = Screen.Register.route) {
            RegisterScreen()
        }
        composable(route = Screen.Detail.route) {
            DetailScreen()
        }
    }
}