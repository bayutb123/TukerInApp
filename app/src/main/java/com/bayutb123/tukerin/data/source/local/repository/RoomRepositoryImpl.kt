package com.bayutb123.tukerin.data.source.local.repository

import com.bayutb123.tukerin.data.EntityMapper
import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val dao: TukerInDao
) : RoomRepository {
    override suspend fun insertAllChat(chat: List<Chat>) {
        dao.insertAllChat(
            EntityMapper.mapChatDomainToEntity(chat)
        )
    }

    override suspend fun insertAllMessage(message: List<Message>) {
        dao.insertAllMessage(
            EntityMapper.mapMessageDomainToEntity(message)
        )
    }

    override suspend fun getAllChats(userId: Int): List<Chat> {
        return EntityMapper.mapChatEntityToDomain(
            dao.getAllChats(userId)
        )
    }

    override suspend fun getAllMessage(chatId: Int): List<Message> {
        return EntityMapper.mapMessageEntityToDomain(
            dao.getAllMessage(chatId)
        )
    }

}