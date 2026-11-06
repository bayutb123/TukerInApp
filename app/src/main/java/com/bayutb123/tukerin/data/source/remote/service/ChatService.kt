package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.chat.AllChatsResponse
import com.bayutb123.tukerin.data.source.remote.response.message.AllMessageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {
    @GET("message/chats/{userId}")
    suspend fun getChats(
        @Path("userId") userId: Int
    ) : Response<AllChatsResponse>

    @GET("message/messages/{chatId}")
    suspend fun getChatMessages(
        @Path("chatId") chatId: Int
    ) : Response<AllMessageResponse>
}