package com.bayutb123.tukerin.data.source.remote.repository

import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.EntityMapper
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.data.source.remote.service.ChatService
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.ChatRepository
import timber.log.Timber
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService,
    private val tukerInDao: TukerInDao
) : ChatRepository {

    override suspend fun getAllChats(userId: Int): NetworkResult<List<Chat>> {
        return try {
            val result = chatService.getChats(userId)
            if (result.isSuccessful) {
                when (result.code()) {
                    200 -> {
                        val body = result.body()
                        val chatList = DataMapper.mapChatResponseToChat(body!!)
                        Timber.d("chatList: $chatList")

                        // Simpan data ke Room
                        tukerInDao.insertAllChat(EntityMapper.mapChatDomainToEntity(chatList, userId))

                        NetworkResult.Success(chatList)
                    }
                    else -> NetworkResult.Error(result.code())
                }
            } else {
                NetworkResult.Error(result.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun getAllMessage(chatId: Int): NetworkResult<List<Message>> {
        return try {
            val result = chatService.getChatMessages(chatId)
            if (result.isSuccessful) {
                when (result.code()) {
                    200 -> {
                        val body = result.body()
                        val messageList = DataMapper.mapMessageResponseToMessage(body!!)

                        // Simpan data ke Room
                        tukerInDao.insertAllMessage(EntityMapper.mapListMessageDomainToListEntity(messageList))

                        NetworkResult.Success(messageList)
                    }
                    else -> NetworkResult.Error(result.code())
                }
            } else {
                NetworkResult.Error(result.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }
}
