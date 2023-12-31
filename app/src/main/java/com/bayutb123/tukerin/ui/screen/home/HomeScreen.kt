package com.bayutb123.tukerin.ui.screen.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.screen.home.chat.chatlist.ChatListScreen
import com.bayutb123.tukerin.ui.screen.home.dashboard.DashboardScreen
import com.bayutb123.tukerin.ui.screen.home.myads.MyAdsScreen
import com.bayutb123.tukerin.ui.screen.home.profile.ProfileScreen

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()
    val navItem = NavItem.items

    Scaffold(
        floatingActionButton = {
            if (homeNavController.currentBackStackEntryAsState()
                    .value
                    ?.destination
                    ?.route == Screen.MyAds.route
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        /* do something */
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Create,
                        contentDescription = "Create",
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "Compose")
                }
            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                NavigationBar {
                    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    fun isSelected(item: NavItem): Boolean {
                        return currentDestination?.hierarchy?.any { it.route == item.route } == true
                    }

                    navItem.forEach { item ->
                        val selected = isSelected(item)

                        NavigationBarItem(
                            icon = {
                                if (selected) {
                                    Icon(imageVector = item.iconSelected, contentDescription = null)
                                } else {
                                    Icon(imageVector = item.icon, contentDescription = null)
                                }
                            },
                            selected = selected,
                            onClick = {
                                homeNavController.navigate(item.route) {
                                    popUpTo(homeNavController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = modifier.padding(paddingValues),
            navController = homeNavController,
            startDestination = Screen.Dashboard.route
        ) {
            addDashboard(onNavigationRequested = onNavigationRequested)
            addChatList(onNavigationRequested = onNavigationRequested)
            addMyAds(onNavigationRequested = onNavigationRequested)
            addProfile(onNavigationRequested = onNavigationRequested)
        }
    }
}

private fun NavGraphBuilder.addDashboard(onNavigationRequested: (String) -> Unit) {
    composable(route = Screen.Dashboard.route) {
        DashboardScreen(
            onNavigationRequested = { destination ->
                onNavigationRequested(destination)
            },
        )
    }
}

private fun NavGraphBuilder.addChatList(onNavigationRequested: (String) -> Unit) {
    composable(route = Screen.ChatList.route) {
        ChatListScreen {
            onNavigationRequested(it)
        }
    }
}

private fun NavGraphBuilder.addMyAds(onNavigationRequested: (String) -> Unit) {
    composable(route = Screen.MyAds.route) {
        MyAdsScreen(
            onNavigationRequested = { destination ->
                onNavigationRequested(destination)
            }
        )
    }
}

private fun NavGraphBuilder.addProfile(onNavigationRequested: (String) -> Unit) {
    composable(route = Screen.Profile.route) {
        ProfileScreen(
            onLogout = {
                // for test only
                onNavigationRequested(Screen.Login.route)
            }
        )
    }
}