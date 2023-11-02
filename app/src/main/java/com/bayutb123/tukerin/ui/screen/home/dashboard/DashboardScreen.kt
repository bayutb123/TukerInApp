package com.bayutb123.tukerin.ui.screen.home.dashboard

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.ui.components.input.CustomSearchBar
import com.bayutb123.tukerin.ui.components.view.ItemGrid
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit,
    onLogoutRequested: (route: String) -> Unit
) {
    val viewModel: DashboardViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    var isInitialized by rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 = isInitialized) {
        if (!isInitialized) {
            viewModel.getAllPost(3)
            isInitialized = true
        }
    }

    @Suppress("UNCHECKED_CAST")
    Scaffold { paddingValues ->
        var text by rememberSaveable { mutableStateOf("") }
        var active by rememberSaveable { mutableStateOf(false) }

        Column(
            modifier = modifier
                .consumeWindowInsets(paddingValues)
                .fillMaxSize()
        ) {
            CustomSearchBar(
                query = text,
                onQueryChange = { text = it },
                active = active,
                onActiveChange = { active = it },
                onSearch = {
                    viewModel.searchPost(text, 3)
                    active = false
                },
                content = {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        ListItem(
                            headlineContent = { Text(resultText) },
                            supportingContent = { Text("Additional info") },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            modifier = Modifier
                                .clickable {
                                    text = resultText
                                    active = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                },
                mainContent = {

                }
            )
            when (state.value) {
                // suppress unchecked cast warning
                is DashboardState.Loading -> {
                    Text(text = "Loading")
                }

                is DashboardState.Success<*> -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items((state.value as DashboardState.Success<*>).data as List<Post>) { item ->
                            ItemGrid(
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        "${item.title} Clicked, seller ${item.ownerName}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                item = item
                            )
                        }
                    }
                }

                else -> {
                    Text(text = "Empty", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
)
@Composable
fun DashboardScreenPreview() {
    TukerInTheme {
        DashboardScreen(
            modifier = Modifier.fillMaxSize(),
            onNavigationRequested = {},
            onLogoutRequested = {}
        )
    }
}