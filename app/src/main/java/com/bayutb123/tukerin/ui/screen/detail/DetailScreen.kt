package com.bayutb123.tukerin.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBackRequested: () -> Unit,
    onNavigationRequested: (route: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackRequested) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                })
        }
    ) { paddingValues ->
        Box(modifier = modifier.padding(paddingValues = paddingValues)) {
            Column {
                Text(text = "Detail Screen")
            }
        }
    }
}