package com.bayutb123.tukerin.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.screen.auth.forgot.ForgotScreen
import com.bayutb123.tukerin.ui.screen.auth.login.LoginScreen
import com.bayutb123.tukerin.ui.screen.auth.register.RegisterScreen
import com.bayutb123.tukerin.ui.screen.detail.DetailScreen
import com.bayutb123.tukerin.ui.screen.home.HomeScreen
import com.bayutb123.tukerin.ui.screen.splash.SplashScreen

@Composable
fun App(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Splash.route ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onAuthenticated = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onNavigationRequested = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onLoginAuthorized = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onBackRequested = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "${Screen.Detail.route}/{postId}",
            arguments = listOf(navArgument(
                name = "postId"
            ) {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            DetailScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onBackRequested = {
                    navController.popBackStack()
                },
                postId = backStackEntry.arguments?.getInt("postId") ?: 0
            )
        }
        composable(route = Screen.Forgot.route) {
            ForgotScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onBackRequested = {
                    navController.popBackStack()
                }
            )
        }

    }
}

