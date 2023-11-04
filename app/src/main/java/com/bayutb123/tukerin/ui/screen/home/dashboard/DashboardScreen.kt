package com.bayutb123.tukerin.ui.screen.home.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Recommend
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.input.CustomSearchBar
import com.bayutb123.tukerin.ui.components.view.ItemGrid
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import kotlinx.coroutines.delay

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit,
    onLogoutRequested: (route: String) -> Unit
) {
    val userId = 5
    val viewModel: DashboardViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    val searchState by viewModel.searchState.collectAsState()
    var isInitialized by rememberSaveable { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isInitialized, key2 = text) {
        if (!isInitialized) {
            viewModel.getAllPost(userId)
            isInitialized = true
        }
        viewModel.setLoading()
        delay(1000)
        viewModel.getSuggestion(userId, text)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = modifier
                .consumeWindowInsets(paddingValues)
                .fillMaxSize()
        ) {
                CustomSearchBar(
                    query = text,
                    onQueryChange = {
                        text = it
                    },
                    active = active,
                    onActiveChange = { active = it },
                    onSearch = {
                        if (text != "") {
                            viewModel.searchPost(text, userId)
                        } else {
                            viewModel.getAllPost(userId)
                        }
                        active = false
                    },
                    content = {
                        searchState.let { searchState ->
                            when (searchState) {
                                is SearchState.Loading -> {
                                    Text(text = "Getting search suggestion")
                                }
                                is SearchState.Success -> {
                                    LazyColumn(
                                        contentPadding = PaddingValues(8.dp)) {
                                        items(searchState.data) { item ->
                                            ListItem(
                                                headlineContent = { Text(item) },
                                                leadingContent = { Icon(Icons.Filled.Recommend, contentDescription = null) },
                                                modifier = Modifier
                                                    .clickable {
                                                        text = item
                                                        active = false
                                                        viewModel.searchPost(text, userId)
                                                    }
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                                            )
                                        }
                                    }
                                }
                                is SearchState.Empty -> {
                                    Text(text = SearchState.Empty("No suggestion").message)
                                }
                            }
                        }

                    },
                    mainContent = {

                    }
                )
            state.let { dashboardStateState ->
                when (dashboardStateState.value) {
                    is DashboardState.Loading -> {
                        Text(text = "Loading")
                    }

                    is DashboardState.Success -> {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            if (state.value is DashboardState.Success) {
                                items((state.value as DashboardState.Success).data) { item ->
                                    ItemGrid(onClick = {}, item = item
                                    )
                                }
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