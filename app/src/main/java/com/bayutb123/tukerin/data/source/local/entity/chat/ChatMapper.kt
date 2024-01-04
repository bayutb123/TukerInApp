package com.bayutb123.tukerin.data.source.local.entity.chat

import com.bayutb123.tukerin.domain.model.Chat

fun ChatEntity.toChat() : Chat {
    return Chat(
        id = this.id,
        userId = this.ownerId,
        receiver = this.awayId,
        context = this.context,
        lastMessageId = this.lastMessageId,
        lastMessage = this.lastMessage,
        createdAt = this.createdAt
    )
}

fun Chat.toChatEntity() : ChatEntity {
    return ChatEntity(
        id = this.id,
        ownerId = userId,
        awayId = this.receiver,
        context = this.context,
        lastMessageId = this.lastMessageId,
        lastMessage = this.lastMessage,
        createdAt = this.createdAt
    )
}

fun List<ChatEntity>.toChatList() : List<Chat> {
    return map {
        Chat(
            id = it.id,
            userId = it.ownerId,
            receiver = it.awayId,
            context = it.context,
            lastMessageId = it.lastMessageId,
            lastMessage = it.lastMessage,
            createdAt = it.createdAt
        )
    }
}

fun List<Chat>.toChatEntityList(userId: Int) : List<ChatEntity> {
    return map { chat ->
        ChatEntity(
            id = chat.id,
            ownerId = userId,
            awayId = chat.receiver,
            context = chat.context,
            lastMessageId = chat.lastMessageId,
            lastMessage = chat.lastMessage,
            createdAt = chat.createdAt
        )
    }
}