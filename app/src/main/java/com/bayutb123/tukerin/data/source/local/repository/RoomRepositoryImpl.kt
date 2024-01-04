package com.bayutb123.tukerin.data.source.local.repository

import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.data.source.local.entity.chat.toChatEntity
import com.bayutb123.tukerin.data.source.local.entity.chat.toChatList
import com.bayutb123.tukerin.data.source.local.entity.message.toMessageEntity
import com.bayutb123.tukerin.data.source.local.entity.message.toMessageList
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.RoomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val dao: TukerInDao
) : RoomRepository {
    override suspend fun insertChat(chat: Chat, userId: Int) {
        dao.insertChat(chat.toChatEntity())
    }

    override suspend fun insertMessage(message: Message) {
        dao.insertMessage(message.toMessageEntity())
    }

    override suspend fun getAllChats(userId: Int): Flow<List<Chat>> {
        return dao.getAllChats(userId)
            .map { it.toChatList() }
    }

    override suspend fun getAllMessage(chatId: Int): Flow<List<Message>> {
        return dao.getAllMessage(chatId)
            .map { it.toMessageList() }
    }
}