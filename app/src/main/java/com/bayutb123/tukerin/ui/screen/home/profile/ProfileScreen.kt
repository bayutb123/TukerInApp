package com.bayutb123.tukerin.ui.screen.home.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bayutb123.tukerin.ui.components.input.FullWidthButton

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
) {
    FullWidthButton(onClick = onLogout, text = "Logout")
}