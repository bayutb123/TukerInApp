package com.bayutb123.tukerin.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chat")
data class ChatEntity(
    @PrimaryKey
    val id: Int,
    val userId: Int,
    val receiverId: Int,
    val context: String,
    val lastMessage: String,
    val createdAt: String,
)
