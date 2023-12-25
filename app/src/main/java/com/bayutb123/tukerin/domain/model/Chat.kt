package com.bayutb123.tukerin.domain.model

data class Chat(
    val id: Int,
    val userId: Int,
    val receiver: User,
    val context: String,
    val lastMessage: Message?,
    val createdAt: String,
)
