package com.bayutb123.tukerin.data.source.remote.repository

import android.util.Log
import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.service.ChatService
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService
) : ChatRepository {
    override suspend fun getAllChats(userId: Int): NetworkResult<List<Chat>> {
        return try {
            val result = chatService.getChats(userId)
            if (result.isSuccessful) {
                when (result.code()) {
                    200 -> {
                        val body = result.body()
                        NetworkResult.Success(DataMapper.mapChatResponseToChat(body!!))
                    }
                    else -> {
                        NetworkResult.Error(result.code())
                    }
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
            Log.d("ChatRepoImpl", chatId.toString())
            Log.d("ChatRepoImpl", chatService.getChatMessages(chatId).toString())
            val result = chatService.getChatMessages(chatId)
            Log.d("ChatRepoImpl", result.code().toString())
            if (result.isSuccessful) {
                when (result.code()) {
                    200 -> {
                        val body = result.body()
                        NetworkResult.Success(DataMapper.mapMessageResponseToMessage(body!!))
                    }
                    else -> {
                        NetworkResult.Error(result.code())
                    }
                }
            } else {
                NetworkResult.Error(result.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

}