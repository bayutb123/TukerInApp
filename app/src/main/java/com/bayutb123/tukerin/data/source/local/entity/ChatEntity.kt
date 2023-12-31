package com.bayutb123.tukerin.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey
    val id: Int,
    val ownerId: Int,
    val awayId: Int,
    val context: String,
    val lastMessageId: Int,
    val lastMessage: String,
    val createdAt: String,
)
