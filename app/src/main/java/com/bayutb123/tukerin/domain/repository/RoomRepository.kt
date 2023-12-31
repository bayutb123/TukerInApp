package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message

interface RoomRepository {

    suspend fun insertAllChat(chat: List<Chat>)
    suspend fun insertAllMessage(message: List<Message>)
    suspend fun getAllChats(userId: Int): List<Chat>
    suspend fun getAllMessage(chatId: Int): List<Message>

}