package com.bayutb123.tukerin.ui.screen.home.myads

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.input.ChipItem
import com.bayutb123.tukerin.ui.components.view.ItemList
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAdsScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (String) -> Unit,
    viewModel: MyAdsViewModel = hiltViewModel()
) {
    var tabIndex by remember { mutableIntStateOf(0) }

    var isAlertVisible by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        viewModel.getMyAds()
    }
    val state by viewModel.state.collectAsState()
    var postId by remember {
        mutableIntStateOf(0)
    }
    var isConfirmDelete by remember { mutableStateOf(false) }
    Scaffold(topBar = { TopAppBar(title = { Text(text = "My Ads") }) }) { it ->
        Column(modifier.padding(it)) {
            TabRow(selectedTabIndex = tabIndex) {
                Tab(selected = tabIndex == 0, onClick = { tabIndex = 0 }, text = { Text("My Ads") })
                Tab(selected = tabIndex == 1, onClick = { tabIndex = 1 }, text = { Text("Transactions") })
            }
            LazyRow(
                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) {
                    ChipItem(text = "Category $it")
                }
            }

            when (state) {
                is MyAdsState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is MyAdsState.Success -> {
                    val data = (state as MyAdsState.Success).data
                    when (tabIndex) {
                        0 -> {
                            LazyColumn(
                                contentPadding = PaddingValues(16.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(data) { item ->
                                    ItemList(
                                        item = item,
                                        onClick = { onNavigationRequested("detail/${item.id}") },
                                        onLongClick = {
                                            scope.launch {
                                                postId = it
                                                viewModel.bottomSheet(BottomSheetState.SHOW)
                                                sheetState.show()
                                            }
                                        },
                                    )
                                }
                            }
                            if (viewModel.bottomSheetState.collectAsState().value == BottomSheetState.SHOW) {
                                ModalBottomSheet(
                                    onDismissRequest = {
                                        scope.launch {
                                            sheetState.hide()
                                            isAlertVisible = false
                                        }.invokeOnCompletion { viewModel.bottomSheet(BottomSheetState.HIDE) }
                                    }, sheetState = sheetState
                                ) {
                                    Text(
                                        text = "Options",
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Column(modifier = Modifier.padding(vertical = 16.dp)) {
                                        ListItem(headlineContent = { Text(text = "Edit") },
                                            supportingContent = { Text(text = "Change name, price, images, and description") },
                                            modifier = Modifier.clickable {
                                                Timber.d("Edit $postId")
                                            })
                                        ListItem(headlineContent = { Text(text = "Unlist") },
                                            supportingContent = { Text(text = "Make this ads not visible to people") },
                                            modifier = Modifier.clickable {
                                                Timber.d("Unlist $postId")
                                            })
                                        ListItem(headlineContent = {
                                            Text(
                                                text = "Delete",
                                                color = Color.Red,
                                                fontWeight = FontWeight.Bold
                                            )
                                        },
                                            supportingContent = {
                                                Text(
                                                    text = "Delete this ads",
                                                    color = Color.Red,
                                                )
                                            },
                                            modifier = Modifier.clickable {
                                                scope.launch {
                                                    isAlertVisible = !isAlertVisible
                                                }
                                            })
                                        AnimatedVisibility(
                                            visible = isAlertVisible
                                        ) {
                                            ListItem(headlineContent = { Text(text = "Confirm delete") },
                                                supportingContent = { Text(text = "Hold for to confirm") },
                                                modifier = Modifier
                                                    .pointerInput(Unit) {
                                                        detectTapGestures(onLongPress = {
                                                            isConfirmDelete = true
                                                            scope.launch {
                                                                if (isConfirmDelete) {
                                                                    scope.launch {
                                                                        sheetState.hide()
                                                                        isAlertVisible = false
                                                                        viewModel.deletePost(postId)
                                                                    }
                                                                }
                                                                isConfirmDelete = false
                                                            }
                                                        })
                                                    }, colors = ListItemDefaults.colors(
                                                    containerColor = MaterialTheme.colorScheme.error,
                                                    headlineColor = MaterialTheme.colorScheme.onError,
                                                    supportingColor = MaterialTheme.colorScheme.onError
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                is MyAdsState.Error -> {
                    Text(text = "Error: ${(state as MyAdsState.Error).msg}")
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMyAdsScreen() {
    TukerInTheme {
        MyAdsScreen(onNavigationRequested = {})
    }
}