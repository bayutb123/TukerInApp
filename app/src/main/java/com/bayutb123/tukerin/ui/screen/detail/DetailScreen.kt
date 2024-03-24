package com.bayutb123.tukerin.ui.screen.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.R
import com.bayutb123.tukerin.core.utils.Currency
import com.bayutb123.tukerin.core.utils.Date
import com.bayutb123.tukerin.core.utils.StringUtils
import com.bayutb123.tukerin.ui.components.view.FullImageView
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
    val context = LocalContext.current
    var phoneNumber by remember {
        mutableStateOf("")
    }
    // create scrollstate
    val scrollState = rememberScrollState()

    val viewModel = hiltViewModel<DetailViewModel>()
    val post = viewModel.post.collectAsStateWithLifecycle()
    var isFullImageVisible by remember { mutableStateOf(false) }
    var imageUrlForFullImage by remember { mutableStateOf("") }

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
        },
        bottomBar = {
            BottomAppBar(actions = {
                Row(Modifier.weight(1f)) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = if (post.value?.isSaved == true) {
                                Icons.Filled.Favorite
                            } else {
                                Icons.Filled.FavoriteBorder
                            },
                            contentDescription = "Save"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Share"
                        )
                    }
                }

                Icon(
                    painter = painterResource(id = R.drawable.whatsapp_svgrepo_com),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    contentDescription = "Chat",
                    modifier = Modifier
                        .clickable {
                            phoneNumber = StringUtils.preparePhoneNumber("081770591289")
                            startWhatsapp(context, phoneNumber)
                        }
                        .padding(16.dp)
                        .size(24.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(4.dp))
                )
            })
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .padding(paddingValues = paddingValues)
                .scrollable(
                    scrollState,
                    orientation = Orientation.Vertical
                )
        ) {
            AnimatedVisibility(visible = isFullImageVisible) {
                FullImageView(imageUrl = imageUrlForFullImage) {
                    isFullImageVisible = false
                }
            }
            Column(
                modifier = modifier
                    .verticalScroll(rememberScrollState())
            ) {
                post.value.let {
                    it?.let {
                        it.images?.let { it1 ->
                            ImagesView(images = it1, onClickImage = { url ->
                                imageUrlForFullImage = url
                                isFullImageVisible = true
                            })
                        }
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
                                sellerImage = "https://cdn-icons-png.flaticon.com/512/2919/2919906.png"
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

fun startWhatsapp(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse("https://api.whatsapp.com/send?phone=$phoneNumber")
    context.startActivity(intent)
}

@Composable
fun ImagesView(
    images: List<String>,
    onClickImage: (String) -> Unit
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
                modifier = Modifier
                    .size(250.dp)
                    .clickable {
                        onClickImage(imageUrl)
                    }
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
            postId = 4,
        )
    }
}