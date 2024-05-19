package com.bayutb123.tukerin.ui.screen.home.myads

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.core.utils.DoubleUtils.toInteger
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.ui.components.input.CustomTextField
import com.bayutb123.tukerin.ui.components.input.FullWidthButton
import com.bayutb123.tukerin.ui.components.input.RatingInput
import com.bayutb123.tukerin.ui.components.view.ContentDialog
import com.bayutb123.tukerin.ui.components.view.ItemList
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import kotlinx.coroutines.CoroutineScope
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
    var tabIndex by rememberSaveable { mutableStateOf(TabIndex.MY_ADS) }
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        viewModel.getMyAds(tabIndex)
    }
    val state by viewModel.state.collectAsState()
    val activePostState by viewModel.activePostState.collectAsState()
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Iklan") }) }) { it ->
        Column(modifier.padding(it)) {
            TabRow(selectedTabIndex = tabIndex.ordinal) {
                Tab(selected = tabIndex == TabIndex.MY_ADS, onClick = {
                    tabIndex = TabIndex.MY_ADS
                    viewModel.getMyAds(tabIndex)
                }, text = { Text("Iklan Saya") })
                Tab(selected = tabIndex == TabIndex.ACTIVE_ADS, onClick = {
                    tabIndex = TabIndex.ACTIVE_ADS
                    viewModel.getMyAds(tabIndex)
                }, text = { Text("Transaksi Aktif") })
            }
//            LazyRow(
//                contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                items(5) {
//                    ChipItem(text = "Category $it")
//                }
//            }

            when (tabIndex) {
                TabIndex.MY_ADS -> {
                    when (state) {
                        is MyAdsState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is MyAdsState.Empty -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = (state as MyAdsState.Empty).msg)
                            }
                        }

                        is MyAdsState.Success -> {
                            val data = (state as MyAdsState.Success).data
                            MyAdsScreen(
                                data,
                                onNavigationRequested,
                                scope,
                                viewModel
                            )
                        }

                        is MyAdsState.Error -> {
                            Text(text = "Error: ${(state as MyAdsState.Error).msg}")
                        }
                    }
                }

                TabIndex.ACTIVE_ADS -> {
                    when (activePostState) {
                        is MyAdsState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        is MyAdsState.Empty -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = (activePostState as MyAdsState.Empty).msg)
                            }
                        }

                        is MyAdsState.Success -> {
                            val data = (activePostState as MyAdsState.Success).data
                            ActiveTransactionScreen(
                                data,
                                onNavigationRequested,
                                scope,
                                viewModel
                            )
                        }

                        is MyAdsState.Error -> {
                            Text(text = "Error: ${(activePostState as MyAdsState.Error).msg}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun MyAdsScreen(
    data: List<Post>,
    onNavigationRequested: (String) -> Unit,
    scope: CoroutineScope,
    viewModel: MyAdsViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    var postId by remember {
        mutableIntStateOf(0)
    }
    var isAlertVisible by remember { mutableStateOf(false) }
    var isConfirmDelete by remember { mutableStateOf(false) }
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
                    viewModel.bottomSheet(BottomSheetState.HIDE)
                }
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
                    supportingContent = { Text(text = "Ganti nama, harga, gambar, dan deskripsi") },
                    modifier = Modifier.clickable {
                        Timber.d("Edit $postId")
                    })
                ListItem(headlineContent = { Text(text = "Unlist") },
                    supportingContent = { Text(text = "Sembunyikan iklan dari orang lain") },
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
                            text = "Hapus iklan",
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
                    ListItem(
                        headlineContent = { Text(text = "Konfirmasi penghapusan") },
                        supportingContent = { Text(text = "Tahan untuk konfirmasi") },
                        modifier = Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(onLongPress = {
                                    isConfirmDelete = true
                                    scope.launch {
                                        if (isConfirmDelete) {
                                            scope.launch {
                                                sheetState.hide()
                                                isAlertVisible = false
                                                viewModel.deletePost(
                                                    postId,
                                                    tabIndex = TabIndex.MY_ADS
                                                )
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

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ActiveTransactionScreen(
    data: List<Post>,
    onNavigationRequested: (String) -> Unit,
    scope: CoroutineScope,
    viewModel: MyAdsViewModel
) {
    val focusManager = LocalFocusManager.current
    val sheetState = rememberModalBottomSheetState()
    var postId by remember {
        mutableIntStateOf(0)
    }
    var review by rememberSaveable {
        mutableStateOf("")
    }
    var rating by rememberSaveable {
        mutableIntStateOf(0)
    }
    var isAlertVisible by remember { mutableStateOf(false) }
    val sellerState by viewModel.sellerState.collectAsState()
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
                        viewModel.getSellerRating(item.ownerId)
                        sheetState.show()
                    }
                },
            )
        }
    }
    if (isAlertVisible) {
        ContentDialog(
            title = { Text(text = "Selesaikan transaksi?") },
            content = {
                Text(text = "apakah anda yakin ingin menyelesaikan transaksi ini?")
                CustomTextField(
                    onTextChanged = { review = it },
                    placeholder = "Rating",
                    onFocus = {
                        scope.launch { sheetState.partialExpand() }
                    }
                )
            },
            dismissButton = {
                TextButton(onClick = { }) {
                    Text(text = "Batal")
                }
            },
            confirmButton = {
                TextButton(onClick = { scope.launch { isAlertVisible = false }
                    .invokeOnCompletion {
                        viewModel.bottomSheet(BottomSheetState.HIDE)
                    }}) {
                    Text(text = "Selesai")
                }
            },
            onDismiss = {
                scope.launch {
                    isAlertVisible = false
                }.invokeOnCompletion { viewModel.bottomSheet(BottomSheetState.HIDE) }
            })
    }
    if (viewModel.bottomSheetState.collectAsState().value == BottomSheetState.SHOW) {
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                    viewModel.bottomSheet(BottomSheetState.HIDE)
                    rating = 0
                }
            }, sheetState = sheetState,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            Text(
                text = "Options",
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Column(modifier = Modifier.padding(16.dp)) {
                SellerCard(sellerState)
                Spacer(modifier = Modifier.height(8.dp))
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Rating")
                    RatingInput(modifier = Modifier.fillMaxWidth()) {
                        rating = it
                    }
                }
                FullWidthButton(text = "Selesaikan transaksi", onClick = {
                    scope.launch {
                        isAlertVisible = true
                    }
                }, enabled = rating != 0)
            }
        }
    }
}

@Composable
private fun SellerCard(sellerState: SellerState) {
    when (sellerState) {
        is SellerState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                CircularProgressIndicator()
            }
        }

        is SellerState.Error -> {
            Text(text = "Error: ${sellerState.message}")
        }

        is SellerState.Seller -> {
            Card(
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(MaterialTheme.colorScheme.primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = sellerState.userRating.name[0].toString().uppercase(),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Column {
                        Text(text = sellerState.userRating.name)
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            repeat(sellerState.userRating.rating.toInteger()) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(text = "${sellerState.userRating.rating} (${sellerState.userRating.reviewCount})")
                        }
                    }
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