package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.repository.ChatRepository
import javax.inject.Inject

class ChatUseCase @Inject constructor(
    private val chatRepository: ChatRepository
) {
    suspend fun getAllChats(userId: Int) {
        chatRepository.getAllChats(userId)
    }

    suspend fun getChatMessages(chatId: Int) {
        chatRepository.getAllMessage(chatId)
    }
}