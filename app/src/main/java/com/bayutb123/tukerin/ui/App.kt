package com.bayutb123.tukerin.ui

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.screen.auth.firebase.GoogleAuthUiClient
import com.bayutb123.tukerin.ui.screen.auth.forgot.ForgotScreen
import com.bayutb123.tukerin.ui.screen.auth.login.LoginScreen
import com.bayutb123.tukerin.ui.screen.auth.login.LoginViewModel
import com.bayutb123.tukerin.ui.screen.auth.register.RegisterScreen
import com.bayutb123.tukerin.ui.screen.detail.DetailScreen
import com.bayutb123.tukerin.ui.screen.home.HomeScreen
import com.google.android.gms.auth.api.identity.Identity

@Composable
fun App(
    modifier: Modifier = Modifier
) {
    val applicationContext = LocalContext.current
    val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }
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
            val viewModel = viewModel<LoginViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = {result ->
                    if (result.resultCode == Activity.RESULT_OK) {

                    }
                }
            )
            LoginScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onLoginAuthorized = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            inclusive = true
                        }
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
        composable(route = Screen.Detail.route) {
            DetailScreen(
                onNavigationRequested = {
                    navController.navigate(it)
                },
                onBackRequested = {
                    navController.popBackStack()
                }
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