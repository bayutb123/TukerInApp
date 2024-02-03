package com.bayutb123.tukerin.ui.screen.post.newpost

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.ui.components.input.CustomTextField
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import com.bayutb123.tukerin.ui.utils.Currency


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostScreen(
    modifier: Modifier = Modifier
) {
    var title by remember {
        mutableStateOf("")
    }
    var imageUris by remember {
        mutableStateOf(listOf<String>())
    }
    var description by remember {
        mutableStateOf("")
    }
    var price by remember {
        mutableStateOf("")
    }
    var priceToDisplayed by remember {
        mutableStateOf("")
    }
    var lat by remember {
        mutableStateOf("")
    }
    var long by remember {
        mutableStateOf("")
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            val tempList = imageUris.toMutableList()
            tempList.add(it.toString())
            imageUris = tempList
            Log.d("NewPostScreen", "onResult: $it")
        }
    )
    val imageUri = listOf("Test", "Test", "Test", "Test")
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "New Post")
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Filled.Done, contentDescription = "Post")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier.padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    item {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .border(
                                    BorderStroke(2.dp, Color.LightGray),
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = null,
                                )
                                Text(text = "Add Image", style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                    items(imageUris) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .background(
                                    color = Color.Transparent,
                                    shape = RoundedCornerShape(4.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            AsyncImage(
                                model = it,
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
                CustomTextField(
                    onTextChanged = {
                        title = it
                    },
                    placeholder = "Title"
                )
                CustomTextField(
                    onTextChanged = {
                        description = it
                    },
                    placeholder = "Description",
                    minLines = 3,
                    maxLines = 10,
                    singleLine = false
                )
                CustomTextField(
                    onTextChanged = {
                        price = it
                        priceToDisplayed = Currency.displayLongAsRupiah(it)
                    },
                    isCurrency = false,
                    placeholder = "Price",
                    keyboardType = KeyboardType.Number
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(0.5f)) {
                        CustomTextField(onTextChanged = {
                            lat = it
                        }, placeholder = "lat", isEnabled = false, isHasDefault = true, defaultText = "321")
                    }
                    Column(modifier = Modifier.weight(0.5f)) {
                        CustomTextField(onTextChanged = {
                            long = it
                        }, placeholder = "long", isEnabled = false, isHasDefault = true, defaultText = "123")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun NewPostScreenPreview() {
    TukerInTheme {
        NewPostScreen()
    }
}