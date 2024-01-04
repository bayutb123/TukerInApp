package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message

interface ChatRepository {
    suspend fun getAllChats(userId: Int)
    suspend fun getAllMessage(chatId: Int)

}