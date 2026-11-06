package com.bayutb123.tukerin.domain.model

data class Message(
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    val receiverId: Int,
    val message: String,
    val attachment: List<String>?,
    val isRead: Boolean,
    val createdAt: String,
)
