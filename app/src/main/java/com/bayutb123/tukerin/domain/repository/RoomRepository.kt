package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message

interface RoomRepository {

    suspend fun insertChat(chat: Chat, userId: Int)
    suspend fun insertMessage(message: Message)
    suspend fun getAllChats(userId: Int): List<Chat>
    suspend fun getAllMessage(chatId: Int): List<Message>

}