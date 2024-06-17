package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.core.utils.Currency
import com.bayutb123.tukerin.core.utils.Date
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import com.bayutb123.tukerin.ui.utils.LocationUtils

@Composable
fun ItemGrid(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    item: Post
) {
    val context = LocalContext.current
    Card(
        onClick = { onClick(0) }, shape = RoundedCornerShape(4.dp)
    ) {
        Column {
            Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter) {

                AsyncImage(
                    model = BuildConfig.apiUrl + "/images/" + item.thumbnailImage,
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(154.dp),
                    contentScale = ContentScale.Crop,
                    filterQuality = FilterQuality.Medium
                )
                if (item.canTradeIn) {
                    Text(
                        text = "Tukar Tambah",
                        modifier = modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
                            .padding(2.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                }
            }
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
                    Text(
                        text = LocationUtils.convertLatLongToCityName(context = context, item.latitude, item.longitude),
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1
                    )
                }
            }
        }
    }
}

@Composable
fun ItemList(
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
    onLongClick: (Int) -> Unit,
    item: Post
) {
    Card(shape = RoundedCornerShape(8.dp), modifier = Modifier.pointerInput(Unit) {
        detectTapGestures(
            onLongPress = {
                onLongClick(item.id)
            },
            onTap = {
                onClick(item.id)
            }
        )
    }) {
        Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.TopEnd) {
            IconButton(onClick = { onLongClick(item.id) }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    modifier = modifier.size(24.dp),
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .height(124.dp)
                    .padding(16.dp)
            ) {
                Box(modifier = modifier.size(92.dp), contentAlignment = Alignment.BottomCenter) {
                    AsyncImage(
                        model = BuildConfig.apiUrl + "/images/" + item.thumbnailImage,
                        contentDescription = item.title,
                        modifier = modifier.size(92.dp),
                        contentScale = ContentScale.Crop
                    )
                    if (item.canTradeIn) {
                        Text(
                            text = "Tukar Tambah",
                            modifier = modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
                                .padding(1.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = MaterialTheme.typography.labelSmall.fontSize
                        )
                    }
                }
                Column(
                    modifier = modifier
                        .weight(1f)
                        .fillMaxHeight(),
                ) {
                    Text(
                        text = Date.formatStringDate(item.createdAt),
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    )
                    Text(
                        text = item.title,
                        modifier = modifier
                            .fillMaxWidth()
                            .weight(1f),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
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
                        thumbnailImage = "https://lh3.googleusercontent.com/SZKYREctLSyeGMbJCovzIxNnW6MmWxcHOhPG5h9UU_bw55iGqG3TBylOuinEBKdB6vW14Nu5CMAkhwQYgu1aRrj8ByEzPvVfn-AaqhKKukQ=s0",
                        ownerId = 1,
                        ownerName = "John Doe",
                        isPublished = true,
                        premium = true,
                        createdAt = "2021-08-01",
                        images = listOf(),
                        address = "Jakarta Pusat",
                        canTradeIn = true,
                        status = "active",
                        updatedAt = "2023-12-23T07:12:57.000000Z",
                        latitude = 0.0,
                        longitude = 0.0
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
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
                        isPublished = true,
                        premium = true,
                        createdAt = "2023-12-23T07:12:57.000000Z",
                        images = listOf(),
                        address = "Jakarta Pusat",
                        canTradeIn = true,
                        status = "active",
                        updatedAt = "2023-12-23T07:12:57.000000Z",
                        latitude = 0.0,
                        longitude = 0.0
                    ),
                    onLongClick = { }
                )
            }
        }
    }
}