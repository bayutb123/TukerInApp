package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    suspend fun getAllChats(userId: Int): Flow<List<Chat>> {
        return roomRepository.getAllChats(userId)
            .map {
                it.sortedByDescending { message ->
                    message.lastMessageId
                }
            }
    }

    suspend fun getAllMessage(chatId: Int): Flow<List<Message>> {
        return roomRepository.getAllMessage(chatId)
    }

}