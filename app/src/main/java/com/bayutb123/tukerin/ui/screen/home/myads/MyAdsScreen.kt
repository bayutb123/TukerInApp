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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.ui.components.input.ChipItem
import com.bayutb123.tukerin.ui.components.view.ItemList
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAdsScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (String) -> Unit,
) {
    // initialize dummy list of strings
    val list = mutableListOf<String>()
    // add some dummy data to the list
    repeat(20) {
        list.add("Item ${it + 1}")
    }
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "My Ads") }) }
    ) { it ->
        Column(Modifier.padding(it)) {
            LazyRow(
                contentPadding = PaddingValues(vertical = 8.dp , horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(5) {
                    ChipItem(text = "Category $it")
                }
            }
            LazyColumn(
                modifier = Modifier,
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = list, key = { item ->
                    item
                }) {
                    ItemList(
                        onClick = {}, item = Post(
                            id = 1,
                            title = "Title",
                            description = "Description",
                            price = 1000000,
                            thumbnailImage = "https://assets.jenius.com/assets/2020/08/15022111/Jenius-Features-.jpg",
                            ownerId = 1,
                            ownerName = "John Doe",
                            active = true,
                            premium = true,
                            createdAt = "2023-12-23T07:12:57.000000Z",
                            images = listOf(),
                            address = "Jakarta Pusat"
                        )
                    )
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