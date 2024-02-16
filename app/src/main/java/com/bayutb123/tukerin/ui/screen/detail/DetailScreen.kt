package com.bayutb123.tukerin.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.core.utils.Currency
import com.bayutb123.tukerin.core.utils.Date
import com.bayutb123.tukerin.ui.components.view.SellerCard
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBackRequested: () -> Unit,
    onNavigationRequested: (route: String) -> Unit,
    postId: Int
) {
    // create scrollstate

    val viewModel = hiltViewModel<DetailViewModel>()
    val post = viewModel.post.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = postId) {
        viewModel.getPost(postId)

    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackRequested) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues = paddingValues)

        ) {
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
            ) {
                post.value.let {
                    it?.let {
                        it.images?.let { it1 -> ImagesView(images = it1) }
                        Date.formatStringDate(it.createdAt)
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            Text(text = it.title)
                            // price
                            Text(
                                text = Currency.convertIntToRupiah(it.price),
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = Date.formatStringDate(it.createdAt))
                            Spacer(modifier = Modifier.height(8.dp))
                            SellerCard(
                                sellerName = it.ownerName,
                                sellerLocation = it.address,
                                sellerImage = "https://media.licdn.com/dms/image/D5603AQEQqJu4ohbltA/profile-displayphoto-shrink_200_200/0/1679033845873?e=1705536000&v=beta&t=w4rpjT0ZRRd1eo49L3TURJGqpXXmFVH7MXlJzLseg8I"
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                                Text(text = it.description)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImagesView(
    modifier: Modifier = Modifier,
    images: List<String>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(images) { index, item ->
            // build string contain image url
            val imageUrl = BuildConfig.apiUrl + "/images/" + item
            AsyncImage(
                model = imageUrl, contentDescription = index.toString(),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(250.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4_XL,
)
@Composable
fun DetailScreenPreview() {
    TukerInTheme {
        DetailScreen(
            onBackRequested = {},
            onNavigationRequested = {},
            postId = 4
        )
    }
}