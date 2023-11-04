package com.bayutb123.tukerin.ui.screen.home.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.input.FullWidthButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
) {
    val viewModel : ProfileViewModel = hiltViewModel()
    FullWidthButton(onClick = {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.logout()
        }
        onLogout()
    }, text = "Logout")
}