package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.ChatRepository
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend fun getAllChats(userId: Int): NetworkResult<List<Chat>> {
        return chatRepository.getAllChats(userId)
    }

    suspend fun getChatMessages(chatId: Int): NetworkResult<List<Message>> {
        return chatRepository.getAllMessage(chatId)
    }
}