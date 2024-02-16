package com.bayutb123.tukerin.ui.screen.post.newpost

import android.content.Context
import android.location.LocationManager
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.bayutb123.tukerin.core.utils.Currency
import com.bayutb123.tukerin.ui.components.input.CustomTextField
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import com.bayutb123.tukerin.ui.utils.PermissionManager
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostScreen(
    modifier: Modifier = Modifier,
    newPostViewModel: NewPostViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var title by remember {
        mutableStateOf("")
    }
    var imageUris by remember {
        mutableStateOf(listOf<Uri>())
    }
    var description by remember {
        mutableStateOf("")
    }
    var price: Long by remember {
        mutableLongStateOf(0)
    }
    var priceToDisplayed by remember {
        mutableStateOf("")
    }
    var lat: Double by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }
    var long: Double by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }
    getUserLocation(context) { latResult, longResult ->
        lat = latResult
        long = longResult
        Timber.tag("Location").d("Lat: $lat, Long: $long")
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            val tempList = imageUris.toMutableList()
            if (it != null) {
                tempList.add(it)
            }
            imageUris = tempList
        }
    )
    val managedActivityResultLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
            // TODO: LOGIC
        }
    LaunchedEffect(key1 = managedActivityResultLauncher) {
        requestPermissions(managedActivityResultLauncher)
    }

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
                    IconButton(onClick = {
                        newPostViewModel.createPost(
                            title,
                            description,
                            imageUris,
                            lat,
                            long,
                            price,
                            context
                        )
                    }) {
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
                        price = it.toLong()
                        priceToDisplayed = Currency.displayLongAsRupiah(it)
                    },
                    isCurrency = false,
                    placeholder = "Price",
                    keyboardType = KeyboardType.Number
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(0.5f)) {
                        CustomTextField(
                            onTextChanged = {
                                lat = it.toDouble()
                            },
                            placeholder = "lat",
                            isEnabled = false,
                            isHasDefault = true,
                            defaultText = lat.toString()
                        )
                    }
                    Column(modifier = Modifier.weight(0.5f)) {
                        CustomTextField(
                            onTextChanged = {
                                long = it.toDouble()
                            },
                            placeholder = "long",
                            isEnabled = false,
                            isHasDefault = true,
                            defaultText = long.toString()
                        )
                    }
                }
            }
        }
    }
}

private fun getUserLocation(context: Context, onLocationObtained: (Double, Double) -> Unit = { _, _ -> }) {
    val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    try {
        val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (location != null) {
            onLocationObtained(location.latitude, location.longitude)
        }
    } catch (e: SecurityException) {
        e.printStackTrace()
    }
}

private fun requestPermissions(requestPermission: ManagedActivityResultLauncher<Array<String>, Map<String, Boolean>>) {
    val galleryPermission = PermissionManager.getPermissions(PermissionManager.Type.GALLERY)
    val locationPermissions = PermissionManager.getPermissions(PermissionManager.Type.LOCATION)
    val permissions = galleryPermission + locationPermissions
    PermissionManager.requestPermission(permissions, requestPermission)
}

@Composable
@Preview(showBackground = true, device = Devices.PIXEL_4)
fun NewPostScreenPreview() {
    TukerInTheme {
        NewPostScreen()
    }
}