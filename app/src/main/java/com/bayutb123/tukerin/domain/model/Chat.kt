package com.bayutb123.tukerin.domain.model

data class Chat(
    val id: Int,
    val userId: Int,
    val receiver: Int,
    val context: String,
    val lastMessage: String,
    val createdAt: String,
)
