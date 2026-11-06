package com.bayutb123.tukerin.data.source.remote.response.message

import com.bayutb123.tukerin.domain.model.Message

fun AllMessageResponse.toMessageList() : List<Message> {
    return this.data.map { message ->
        Message(
            id = message.id,
            chatId = message.chatId,
            senderId = message.senderId,
            receiverId = message.receiverId,
            message = message.message,
            attachment = message.attachments.map { it.image },
            isRead = message.isRead == 1,
            createdAt = message.createdAt
        )
    }
}