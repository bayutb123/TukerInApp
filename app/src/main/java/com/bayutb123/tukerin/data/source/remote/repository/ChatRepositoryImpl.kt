package com.bayutb123.tukerin.data.source.remote.repository

import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.data.source.local.entity.chat.toChatEntityList
import com.bayutb123.tukerin.data.source.local.entity.message.toMessageEntityList
import com.bayutb123.tukerin.data.source.remote.response.chat.toChatList
import com.bayutb123.tukerin.data.source.remote.response.message.toMessageList
import com.bayutb123.tukerin.data.source.remote.service.ChatService
import com.bayutb123.tukerin.domain.repository.ChatRepository
import timber.log.Timber
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService,
    private val tukerInDao: TukerInDao
) : ChatRepository {

    override suspend fun getAllChats(userId: Int) {
        try {
            val result = chatService.getChats(userId)
            if (result.isSuccessful) {
                when (result.code()) {
                    200 -> {
                        val body = result.body()
                        val chatList = body!!.toChatList()
                        tukerInDao.insertChat(chatList.toChatEntityList(userId))
                        Timber.d("Chat list: $chatList")
                    }
                    else -> {
                        Timber.d(result.code().toString())
                    }
                }
            } else {
                Timber.d(result.errorBody().toString())
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    override suspend fun getAllMessage(chatId: Int) {
        try {
            val result = chatService.getChatMessages(chatId)
            if (result.isSuccessful) {
                when (result.code()) {
                    200 -> {
                        val body = result.body()
                        val messageList = body!!.toMessageList()
                        tukerInDao.insertMessage(messageList.toMessageEntityList())
                        Timber.d("Message list: $messageList")
                    }
                    else -> {
                        Timber.d(result.code().toString())
                    }
                }
            } else {
                Timber.d(result.errorBody().toString())
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}
