package com.bayutb123.tukerin.data.source.remote.response.chat

import com.bayutb123.tukerin.domain.model.Chat

fun AllChatsResponse.toChatList() : List<Chat> {
    return this.data.map { chat ->
        Chat(
            id = chat.id,
            userId = chat.userId,
            receiver = chat.receiver.id,
            context = chat.context,
            lastMessageId = chat.lastMessage.chatId,
            lastMessage = chat.lastMessage.message,
            createdAt = chat.createdAt
        )
    }
}