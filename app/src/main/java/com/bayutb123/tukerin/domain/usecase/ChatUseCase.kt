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
        return when (val result = chatRepository.getAllChats(userId)) {
            is NetworkResult.Success -> {
                val reversedChats = result.data!!.reversed()
                NetworkResult.Success(reversedChats)
            }
            else -> {
                result
            }
        }

    }

    suspend fun getChatMessages(chatId: Int): NetworkResult<List<Message>> {
        return chatRepository.getAllMessage(chatId)
    }
}