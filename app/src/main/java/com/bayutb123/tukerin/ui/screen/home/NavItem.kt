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
    val icon: ImageVector,
    val iconDisabled: ImageVector? = null
) {
    companion object {
        val items = listOf(
            NavItem(
                route = Screen.Dashboard.route,
                title = "Dashboard",
                icon = Icons.Default.Dashboard,
                iconDisabled = Icons.Outlined.Dashboard
            ),
            NavItem(
                route = Screen.Saved.route,
                title = "Saved",
                icon = Icons.Default.Bookmarks,
                iconDisabled = Icons.Outlined.Bookmarks
            ),
            NavItem(
                route = Screen.Profile.route,
                title = "Profile",
                icon = Icons.Default.AccountCircle,
                iconDisabled = Icons.Outlined.AccountCircle
            )
        )
    }
}