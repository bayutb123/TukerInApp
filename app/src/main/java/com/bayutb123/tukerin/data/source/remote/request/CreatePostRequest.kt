package com.bayutb123.tukerin.data.source.remote.request

import android.net.Uri

data class CreatePostRequest(
    val userId: Int,
    val title: String,
    val content: String,
    val images: List<Uri>,
    val lat: Double,
    val long: Double,
    val price: Long
)
