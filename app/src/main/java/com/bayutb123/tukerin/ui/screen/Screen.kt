package com.bayutb123.tukerin.ui.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Dashboard : Screen("dashboard")
    object Detail : Screen("detail")
    object Profile : Screen("profile")
    object Saved : Screen("saved")
    object Login : Screen("login")
    object Register : Screen("register")

}
