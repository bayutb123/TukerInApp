package com.bayutb123.tukerin.domain.repository

interface ChatRepository {
    suspend fun getAllChats(userId: Int)
    suspend fun getAllMessage(chatId: Int)

}