package com.bayutb123.tukerin.ui.screen.home.myads

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.input.ChipItem
import com.bayutb123.tukerin.ui.components.view.ItemList
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAdsScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (String) -> Unit,
    viewModel: MyAdsViewModel = hiltViewModel()
) {
    viewModel.getMyAds()
    val state by viewModel.state.collectAsState()
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "My Ads") }) }
    ) { it ->
        Column(modifier.padding(it)) {
            LazyRow(
                contentPadding = PaddingValues(vertical = 8.dp , horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) {
                    ChipItem(text = "Category $it")
                }
            }
            when (state) {
                is MyAdsState.Loading -> {
                    Text(text = "Loading")
                }
                is MyAdsState.Success -> {
                    val data = (state as MyAdsState.Success).data
                    LazyColumn {
                        items(data) { item ->
                            ItemList(
                                item = item,
                                onClick = { onNavigationRequested("detail/${item.id}") }
                            )
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
        MyAdsScreen(
            onNavigationRequested = {}
        )
    }
}