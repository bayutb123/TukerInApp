package com.bayutb123.tukerin.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class MessageEntity(
    @PrimaryKey
    val id: Int,
    val chatId: Int,
    val senderId: Int,
    val receiverId: Int,
    val message: String,
    val attachment: String?,
    val isRead: Boolean,
    val createdAt: String,
)