package com.bayutb123.tukerin.domain.model

data class Post(
    val id: Int,
    val title: String,
    val description: String,
    val address : String,
    val price: Int,
    val thumbnailImage : String,
    val ownerId: Int,
    val ownerName: String,
    val active: Boolean,
    val premium: Boolean,
    val createdAt: String,
    val images: List<String>? = null
)

