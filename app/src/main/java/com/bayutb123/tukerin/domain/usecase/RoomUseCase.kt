package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.RoomRepository
import javax.inject.Inject

class RoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {

    suspend fun insertChat(chat: Chat, userId: Int) {
        roomRepository.insertChat(chat, userId)
    }

    suspend fun insertMessage(message: Message) {
        roomRepository.insertMessage(message)
    }

    suspend fun getAllChats(userId: Int): List<Chat> {
        return roomRepository.getAllChats(userId).sortedBy { it.lastMessageId }.reversed()
    }

    suspend fun getAllMessage(chatId: Int): List<Message> {
        return roomRepository.getAllMessage(chatId)
    }

}