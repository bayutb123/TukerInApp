package com.bayutb123.tukerin.ui.screen.home.dashboard

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Recommend
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bayutb123.tukerin.ui.components.input.CustomSearchBar
import com.bayutb123.tukerin.ui.components.view.ItemGrid
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import kotlinx.coroutines.delay

@SuppressLint("UnrememberedMutableState")
@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchState by viewModel.searchState.collectAsStateWithLifecycle()
    val fabState by viewModel.fetchState.collectAsStateWithLifecycle()

    var searchText by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var isInitiated by remember { mutableStateOf(false) }

    // LazyGridState
    val lazyGridState = rememberLazyGridState()
    val isLastItemVisible by derivedStateOf {
        lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyGridState.layoutInfo.totalItemsCount - 1
    }

    DisposableEffect(Unit) {
        if (viewModel.checkConnection(context) && !isInitiated) {
            viewModel.getAllPost(isReset = true, context = context)
        }
        onDispose {
            isInitiated = true
        }
    }

    LaunchedEffect(key1 = searchText) {
        if (viewModel.checkConnection(context)) {
            // Search suggestion
            delay(1000)
            viewModel.setLoading()
            viewModel.getSuggestion(searchText)
        }
    }

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = isLastItemVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                ExtendedFloatingActionButton(onClick = {
                    viewModel.getAllPost(context = context)
                }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.height(48.dp)
                    ) {
                        AnimatedVisibility(visible = fabState) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                        Text(text = "Load More")
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(
            modifier = modifier
                .consumeWindowInsets(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            CustomSearchBar(
                onSearch = {
                    if (searchText != "") {
                        viewModel.searchPost(searchText)
                    } else {
                        viewModel.getAllPost(true, context = context)
                    }
                    isSearching = false
                },
                onQueryChange = {
                    searchText = it
                },
                query = searchText,
                active = isSearching,
                onActiveChange = { isSearching = it },
                content = {
                    searchState.let { searchState ->
                        when (searchState) {
                            is SearchState.Loading -> {
                                Box(
                                    modifier = modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "Working on it!", textAlign = TextAlign.Center)
                                }
                            }

                            is SearchState.Success -> {
                                LazyColumn(
                                    contentPadding = PaddingValues(8.dp)
                                ) {
                                    items(searchState.data) { item ->
                                        ListItem(
                                            headlineContent = { Text(item) },
                                            leadingContent = {
                                                Icon(
                                                    Icons.Filled.Recommend,
                                                    contentDescription = null
                                                )
                                            },
                                            modifier = Modifier
                                                .clickable {
                                                    searchText = item
                                                    isSearching = false
                                                    viewModel.searchPost(searchText)
                                                }
                                                .fillMaxWidth()
                                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                        )
                                    }
                                }
                            }

                            is SearchState.Empty -> {
                                Box(
                                    modifier = modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = searchState.message, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }

                }
            ) {

            }
            state.let { dashboardState ->
                when (dashboardState) {
                    is DashboardState.Loading -> {
                        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            LinearProgressIndicator()
                        }
                    }

                    is DashboardState.Success -> {
                        Column(
                            modifier = modifier.fillMaxSize(),
                            horizontalAlignment = CenterHorizontally
                        ) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(bottom = 16.dp, start = 16.dp, end = 16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f),
                                state = lazyGridState
                            ) {
                                items(dashboardState.data) { item ->
                                    ItemGrid(
                                        onClick = {
                                            onNavigationRequested(Screen.Detail.route + "/${item.id}")
                                        }, item = item
                                    )
                                }
                            }

                        }

                    }

                    is DashboardState.Failed -> {
                        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = dashboardState.message, textAlign = TextAlign.Center)
                        }
                    }

                    else -> {
                        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(text = "No result")
                        }
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
        )
    }
}