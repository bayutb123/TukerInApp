package com.bayutb123.tukerin.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    onBackRequested: () -> Unit,
    onNavigationRequested: (route: String) -> Unit,
    postId: Int = 12
) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val post = viewModel.post.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = viewModel) {
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
        Box(modifier = modifier.padding(paddingValues = paddingValues)) {
            Column {
                post.value?.let {
                    Text(text = it.title)
                    Text(text = it.description)
                    it.images.forEach {imageUrl ->
                        AsyncImage(model = "${BuildConfig.apiUrl}/images/$imageUrl", contentDescription = it.title)
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
            .height(200.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        itemsIndexed(images) { index, item ->
            AsyncImage(model = item, contentDescription = index.toString(),
                contentScale = ContentScale.Crop
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
            onNavigationRequested = {}
        )
    }
}