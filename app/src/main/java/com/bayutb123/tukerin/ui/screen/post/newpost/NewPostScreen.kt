package com.bayutb123.tukerin.ui.screen.post.newpost

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.bayutb123.tukerin.core.utils.PermissionManager
import com.bayutb123.tukerin.core.utils.SystemUtils
import com.bayutb123.tukerin.ui.components.input.CustomDropDown
import com.bayutb123.tukerin.ui.components.input.CustomTextField
import com.bayutb123.tukerin.ui.components.view.CustomAlertDialog
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import kotlinx.coroutines.launch
import timber.log.Timber


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPostScreen(
    modifier: Modifier = Modifier,
    newPostViewModel: NewPostViewModel = hiltViewModel(),
    onBackRequested: () -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        newPostViewModel.getPostCategories()
    }
    val categories by newPostViewModel.postCategories.collectAsStateWithLifecycle()
    val subCategories by newPostViewModel.subCategories.collectAsStateWithLifecycle()
    val isSubCategoryIsSelected = subCategories is SubCategoryState.Success
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
    var selectedCategory by remember {
        mutableStateOf("")
    }
    var selectedSubCategory by remember {
        mutableStateOf("")
    }
    var canTrade: Boolean by rememberSaveable {
        mutableStateOf(false)
    }
    var kecamatan by remember {
        mutableStateOf("")
    }
    var kota by remember {
        mutableStateOf("")
    }
    var isSuccess by remember { mutableStateOf(false) }
    var isFailed by remember { mutableStateOf(false) }
    LaunchedEffect(key1 = Unit) {
        launch {
            SystemUtils.getUserLongLatAlt(
                context,
                onLongLatAltObtained = { latResult, longResult, _ ->
                    Timber.d("lat: $latResult, long: $longResult")
                    newPostViewModel.updateLocation(latResult, longResult)
                },
                onFailure = {
                    Timber.e(it)
                }
            )
        }

        launch {
            SystemUtils.getUserLocation(context, onCityObtained = { city, kec ->
                kecamatan = kec
                kota = city
            }, onFailure = {})
        }
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
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) { map ->
            val isGranted = map.values.all { it }
            if (!isGranted) {
//                onBackRequested()
            }
        }
    LaunchedEffect(key1 = managedActivityResultLauncher) {
        requestPermissions(managedActivityResultLauncher)
    }
    val state by newPostViewModel.newPostState.collectAsState()
    ObserveState(
        state,
        onPostSuccess = {
            isSuccess = true
        },
        onError = {
            isFailed = true
        }
    )
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "New Post")
                },
                navigationIcon = {
                    IconButton(onClick = onBackRequested) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        val categoryResult = if (isSubCategoryIsSelected) {
                            selectedSubCategory
                        } else {
                            selectedCategory
                        }
                        newPostViewModel.createPost(
                            title,
                            description,
                            imageUris,
                            price,
                            categoryResult,
                            canTrade,
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
            CustomAlertDialog(
                title = "Success",
                message = "Post has been created",
                isVisible = isSuccess,
                onConfirm = {
                    isSuccess = false
                    onBackRequested()
                },
                dismissEnabled = false
            )
            CustomAlertDialog(
                title = "Failed",
                message = "Post failed to create",
                isVisible = isFailed,
                onConfirm = {
                    isFailed = false
                },
                dismissEnabled = false
            )

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
                                    if (!newPostViewModel.isLoading) {
                                        launcher.launch("image/*")
                                    }
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
                    isEnabled = !newPostViewModel.isLoading,
                    placeholder = "Title"
                )

                CustomTextField(
                    onTextChanged = {
                        description = it
                    },
                    isEnabled = !newPostViewModel.isLoading,
                    placeholder = "Description",
                    minLines = 3,
                    maxLines = 10,
                    singleLine = false
                )

                CustomTextField(
                    onTextChanged = {
                        if (it.isNotEmpty()) {
                            // replace all except 0 - 9
                            val regex = Regex("[^0-9]")
                            price = it.replace(regex, "").toLong()
                        }
                    },
                    isCurrency = true,
                    isEnabled = !newPostViewModel.isLoading,
                    placeholder = "Price",
                    keyboardType = KeyboardType.Number
                )

                if (categories.isNotEmpty()) {
                    CustomDropDown(items = categories, selectedItem = categories[0].name) {
                        selectedCategory = it.name
                        newPostViewModel.getPostSubCategory(it.id)
                    }
                } else {
                    CustomDropDown(items = categories, selectedItem = selectedCategory) {
                        selectedCategory = it.name
                        newPostViewModel.getPostSubCategory(it.id)
                    }
                }

                AnimatedVisibility(
                    visible = subCategories is SubCategoryState.Success,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    when (val subCategory = subCategories) {
                        is SubCategoryState.Success -> {
                            if (subCategory.data.isNotEmpty()) {
                                CustomDropDown(
                                    items = subCategory.data,
                                    selectedItem = subCategory.data[0].name
                                ) {
                                    selectedSubCategory = it.name
                                }
                            }
                        }

                        else -> {
                        }
                    }
                }


                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Bisa tukar tambah?")
                        AnimatedVisibility(visible = canTrade) {
                            Text(
                                text = "Mantap! Barang yang dipost kini bisa ditukar tambah",
                                fontSize = MaterialTheme.typography.bodySmall.fontSize
                            )
                        }
                    }
                    Switch(checked = canTrade, onCheckedChange = { canTrade = it })
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Column(modifier = Modifier.weight(0.5f)) {
                        Text(text = "Lokasi saat ini: $kecamatan, $kota")
                    }
                }
            }
            AnimatedVisibility(
                visible = newPostViewModel.isLoading,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun ObserveState(
    newPostState: NewPostState,
    onPostSuccess: () -> Unit,
    onError: () -> Unit = {}
) {
    when (newPostState) {
        is NewPostState.Success -> {
            onPostSuccess()
        }

        is NewPostState.Loading -> {
        }

        is NewPostState.Failed -> {
            onError()
        }

        else -> {
        }
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
        NewPostScreen(onBackRequested = { /*TODO*/ })
    }
}