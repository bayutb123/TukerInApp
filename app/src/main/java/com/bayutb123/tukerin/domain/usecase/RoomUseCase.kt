package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.RoomRepository
import javax.inject.Inject

class RoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {

    suspend fun insertAllChat(chat: List<Chat>) {
        roomRepository.insertAllChat(chat)
    }

    suspend fun insertAllMessage(message: List<Message>) {
        roomRepository.insertAllMessage(message)
    }

    suspend fun getAllChats(userId: Int): List<Chat> {
        return roomRepository.getAllChats(userId)
    }

    suspend fun getAllMessage(chatId: Int): List<Message> {
        return roomRepository.getAllMessage(chatId)
    }

}