package com.bayutb123.tukerin.domain.model

data class Post(
    val id: Int,
    val title: String,
    val description: String,
    val address : String,
    val price: Long,
    val thumbnailImage : String?,
    val ownerId: Int,
    val ownerName: String,
    val status: String,
    val isPublished: Boolean,
    val premium: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val images: List<String>? = null,
    val isSaved: Boolean = false,
    val canTradeIn: Boolean = false
)


