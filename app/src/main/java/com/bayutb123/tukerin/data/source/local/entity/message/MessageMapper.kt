package com.bayutb123.tukerin.data.source.local.entity.message

import com.bayutb123.tukerin.domain.model.Message

fun MessageEntity.toMessage() : Message {
    return Message(
        id = this.id,
        chatId = this.chatId,
        senderId = this.senderId,
        receiverId = this.receiverId,
        message = this.message,
        attachment = this.attachment?.let { s -> listOf(s) },
        isRead = this.isRead,
        createdAt = this.createdAt
    )
}

fun Message.toMessageEntity() : MessageEntity {
    return MessageEntity(
        id = this.id,
        chatId = this.chatId,
        senderId = this.senderId,
        receiverId = this.receiverId,
        message = this.message,
        attachment = this.attachment?.get(0),
        isRead = this.isRead,
        createdAt = this.createdAt
    )
}

fun List<MessageEntity>.toMessageList() : List<Message> {
    return map {
        Message(
            id = it.id,
            chatId = it.chatId,
            senderId = it.senderId,
            receiverId = it.receiverId,
            message = it.message,
            attachment = it.attachment?.let { s -> listOf(s) },
            isRead = it.isRead,
            createdAt = it.createdAt
        )
    }
}

fun List<Message>.toMessageEntityList() : List<MessageEntity> {
    return map { message ->
        MessageEntity(
            id = message.id,
            chatId = message.chatId,
            senderId = message.senderId,
            receiverId = message.receiverId,
            message = message.message,
            attachment = message.attachment?.get(0),
            isRead = message.isRead,
            createdAt = message.createdAt
        )
    }
}