package com.bayutb123.tukerin.data.source.remote.request

import android.net.Uri

data class CreatePostRequest(
    val userId: Int,
    val title: String,
    val description: String,
    val images: List<Uri>,
    val lat: Long,
    val long: Long,
    val price: Long
)
