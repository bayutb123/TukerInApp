package com.bayutb123.tukerin.ui.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Dashboard : Screen("dashboard")
    object Detail : Screen("detail")
    object Profile : Screen("profile")
    object Saved : Screen("saved")
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Forgot : Screen("forgot")
    object Register : Screen("register")
    object MyAds : Screen("my-ads")

}
