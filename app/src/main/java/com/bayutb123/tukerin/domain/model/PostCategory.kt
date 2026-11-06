package com.bayutb123.tukerin.domain.model

data class PostCategory(
    val id: Int,
    val parentId: Int,
    val name: String,
    val description: String,
    val createdAt: String,
    val updatedAt: String
)
