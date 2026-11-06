package com.bayutb123.tukerin.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
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
import com.bayutb123.tukerin.ui.screen.home.chat.chatroom.ChatRoomScreen
import com.bayutb123.tukerin.ui.screen.post.newpost.NewPostScreen
import com.bayutb123.tukerin.ui.screen.splash.SplashScreen

@Composable
fun App(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionViewModel: SessionViewModel = hiltViewModel()

    // The API rejected the saved token (e.g. it was revoked): SessionManager
    // has already cleared the user, so leave whatever screen is open and go
    // to login, as the Logout button does.
    LaunchedEffect(Unit) {
        sessionViewModel.sessionExpired.collect {
            Toast.makeText(
                context,
                "Your session has expired. Please log in again.",
                Toast.LENGTH_LONG
            ).show()
            navController.navigate(Screen.Login.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    NavHost(navController = navController, startDestination = Screen.Splash.route ) {
        composable(route = Screen.Home.route) {
            HomeScreen { route, isFinish ->
                navController.navigate(route) {
                    if (isFinish) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
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
                onRegisterSuccess = {
                    navController.navigate(it) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
                postId = backStackEntry.arguments?.getInt("postId") ?: 0,
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
        composable(route = Screen.ChatRoom.route + "/{chatId}",
            arguments = listOf(
                navArgument("chatId") {
                    type = NavType.IntType
                },
            )
        ) {
            ChatRoomScreen(
                chatId = it.arguments?.getInt("chatId") ?: 0,
            )
        }
        composable(route = Screen.CreateAds.route) {
            NewPostScreen {
                navController.popBackStack(Screen.Home.route, false)
            }
        }
    }
}

