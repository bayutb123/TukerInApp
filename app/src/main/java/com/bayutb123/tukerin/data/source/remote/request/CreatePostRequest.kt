package com.bayutb123.tukerin.data.source.remote.request

import android.net.Uri

data class CreatePostRequest(
    val userId: Int,
    val title: String,
    val content: String,
    val images: List<Uri>,
    val lat: Double,
    val long: Double,
    val price: Long,
    val type: String,
    val canTrade: Boolean
)

fun CreatePostRequest.validate() : Boolean {
    return title.isNotEmpty() && content.isNotEmpty() && images.isNotEmpty() && price > 0
}