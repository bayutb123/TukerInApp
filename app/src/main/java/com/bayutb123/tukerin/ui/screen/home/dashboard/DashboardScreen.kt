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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
) {
    val context = LocalContext.current
    val userId = 5
    val viewModel: DashboardViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()
    val searchState by viewModel.searchState.collectAsState()
    var isInitialized by rememberSaveable { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // LazyGridState
    val lazyGridState = rememberLazyGridState()
    val isLastItemVisible by derivedStateOf {
        lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == lazyGridState.layoutInfo.totalItemsCount - 1
    }

    // FAB state
    val fabState by viewModel.fetchState.collectAsState()

    LaunchedEffect(key1 = isInitialized, key2 = text) {
        if (viewModel.checkConnection(context)) {
            if (!isInitialized) {
                viewModel.getAllPost(userId, context = context)
                isInitialized = true
            }
            // Search suggestion
            delay(1000)
            viewModel.setLoading()
            viewModel.getSuggestion(userId, text)
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
                    viewModel.getAllPost(userId, context = context)
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
            horizontalAlignment = CenterHorizontally
        ) {
            CustomSearchBar(
                onSearch = {
                    if (text != "") {
                        viewModel.searchPost(text, userId)
                    } else {
                        viewModel.getAllPost(userId, true, context = context)
                    }
                    active = false
                },
                onQueryChange = {
                    text = it
                },
                query = text,
                active = active,
                onActiveChange = { active = it },
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
            state.let { dashboardStateState ->
                when (dashboardStateState) {
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
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.weight(1f),
                                state = lazyGridState
                            ) {
                                items(dashboardStateState.data) { item ->
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
                            Text(text = dashboardStateState.message, textAlign = TextAlign.Center)
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