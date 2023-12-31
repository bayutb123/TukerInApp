package com.bayutb123.tukerin.data

import com.bayutb123.tukerin.data.source.local.entity.ChatEntity
import com.bayutb123.tukerin.data.source.local.entity.MessageEntity
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message

class EntityMapper {
    companion object {
        fun mapMessageEntityToDomain(messageEntity: List<MessageEntity>) : List<Message> {
            val messageList = ArrayList<Message>()
            messageEntity.map {
                val message = Message(
                    id = it.id,
                    chatId = it.chatId,
                    senderId = it.senderId,
                    receiverId = it.receiverId,
                    message = it.message,
                    attachment = it.attachment?.let { s -> listOf(s) },
                    isRead = it.isRead,
                    createdAt = it.createdAt
                )
                messageList.add(message)
            }
            return messageList
        }

        fun mapChatEntityToDomain(chatEntity: List<ChatEntity>) : List<Chat> {
            val chatList = ArrayList<Chat>()
            chatEntity.map {
                val chat = Chat(
                    id = it.id,
                    userId = it.userId,
                    receiver = it.receiverId,
                    context = it.context,
                    lastMessage = it.lastMessage,
                    createdAt = it.createdAt
                )
                chatList.add(chat)
            }
            return chatList
        }

        fun mapMessageDomainToEntity(message: List<Message>) : List<MessageEntity> {
            val messageList = ArrayList<MessageEntity>()
            message.map {
                val messageEntity = MessageEntity(
                    id = it.id,
                    chatId = it.chatId,
                    senderId = it.senderId,
                    receiverId = it.receiverId,
                    message = it.message,
                    attachment = it.attachment?.get(0),
                    isRead = it.isRead,
                    createdAt = it.createdAt
                )
                messageList.add(messageEntity)
            }
            return messageList
        }

        fun mapChatDomainToEntity(chat: List<Chat>) : List<ChatEntity> {
            val chatList = ArrayList<ChatEntity>()
            chat.map {
                val chatEntity = ChatEntity(
                    id = it.id,
                    userId = it.userId,
                    receiverId = it.receiver,
                    context = it.context,
                    lastMessage = it.lastMessage,
                    createdAt = it.createdAt
                )
                chatList.add(chatEntity)
            }
            return chatList
        }
    }
}