package com.bayutb123.tukerin.ui.screen.home.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bayutb123.tukerin.ui.components.input.FullWidthButton
import com.bayutb123.tukerin.ui.screen.Screen

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit,
    onLogoutRequested: (route: String) -> Unit
) {
    Column {
        FullWidthButton(
            onClick = { onNavigationRequested(Screen.Detail.route) },
            text = "Go to Detail"
        )
        FullWidthButton(onClick = { onLogoutRequested(Screen.Login.route) }, text = "Logout")
    }
}