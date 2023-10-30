package com.bayutb123.tukerin.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val navItem = NavItem.items
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                navItem.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            if (currentDestination?.hierarchy?.any { it.route == item.route } == true) {
                                Icon(imageVector = item.iconSelected, contentDescription = null)
                            } else {
                                Icon(imageVector = item.icon, contentDescription = null)
                            } },
                        label = { Text(item.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            homeNavController.navigate(item.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(homeNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(modifier = modifier.padding(paddingValues) , navController = homeNavController, startDestination = Screen.Dashboard.route) {
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
}