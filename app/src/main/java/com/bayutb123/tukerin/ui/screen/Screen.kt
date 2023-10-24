package com.bayutb123.tukerin.ui.screen

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Detail : Screen("detail")
    object login : Screen("login")
    object register : Screen("register")
}
