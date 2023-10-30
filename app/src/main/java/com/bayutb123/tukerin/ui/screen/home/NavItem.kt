package com.bayutb123.tukerin.ui.screen.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.ui.graphics.vector.ImageVector
import com.bayutb123.tukerin.ui.screen.Screen

data class NavItem(
    val route: String,
    val title: String,
    val iconSelected: ImageVector,
    val icon: ImageVector
) {
    companion object {
        val items = listOf(
            NavItem(
                route = Screen.Dashboard.route,
                title = "Dashboard",
                iconSelected = Icons.Default.Dashboard,
                icon = Icons.Outlined.Dashboard
            ),
            NavItem(
                route = Screen.Saved.route,
                title = "Saved",
                iconSelected = Icons.Default.Bookmarks,
                icon = Icons.Outlined.Bookmarks
            ),
            NavItem(
                route = Screen.Profile.route,
                title = "Profile",
                iconSelected = Icons.Default.AccountCircle,
                icon = Icons.Outlined.AccountCircle
            )
        )
    }
}