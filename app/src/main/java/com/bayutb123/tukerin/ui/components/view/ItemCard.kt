package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import com.bayutb123.tukerin.ui.utils.Currency
import com.bayutb123.tukerin.ui.utils.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemGrid(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    isPremium: Boolean = false,
    item: Post
) {
    Card(
        onClick = { onClick(0) },shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = modifier.fillMaxWidth()) {

            Column {
                AsyncImage(
                    model = BuildConfig.apiUrl + "/images/" + item.thumbnailImage,
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(154.dp),
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.Medium
                )
                Column(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                ) {
                    Text(
                        text = Currency.convertIntToRupiah(item.price),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier.fillMaxWidth(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Row(
                        modifier = modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            modifier = modifier.size(12.dp)
                        )
                        Spacer(modifier = modifier.width(2.dp))
                        Text(text = item.address, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
            if (isPremium) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = "Premium",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = modifier
                            .padding(4.dp)
                            .background(
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.shapes.small
                            )
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    item: Post
) {
    Card(onClick = { onClick(item.id) }, shape = RoundedCornerShape(8.dp)) {
        Box(modifier = modifier.fillMaxWidth()) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .height(140.dp)
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = item.thumbnailImage,
                    contentDescription = item.title,
                    modifier = modifier.size(108.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Text(text = Date().formatStringDate(item.createdAt),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize)
                    Text(
                        text = item.title,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                    if (item.premium) {
                        Text(
                            text = "Premium Ad",
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        )
                    }
                    Text(
                        text = Currency.convertIntToRupiah(item.price),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemGrid() {
    TukerInTheme {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(20) {
                ItemGrid(
                    onClick = {},
                    item = Post(
                        id = 1,
                        title = "Title",
                        description = "Description",
                        price = 1000000,
                        thumbnailImage = "https://assets.jenius.com/assets/2020/08/15022111/Jenius-Features-.jpg",
                        ownerId = 1,
                        ownerName = "John Doe",
                        active = true,
                        premium = true,
                        createdAt = "2021-08-01",
                        images = listOf(),
                        address = "Jakarta Pusat"
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewItemList() {
    TukerInTheme {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(16.dp)) {
            items(20) {
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