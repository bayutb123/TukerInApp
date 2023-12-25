package com.bayutb123.tukerin.data.source.remote.service

import com.bayutb123.tukerin.data.source.remote.response.chat.AllChatsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ChatService {
    @GET("message/chats/{userId}")
    suspend fun getChats(
        @Path("userId") userId: Int
    ) : Response<AllChatsResponse>

    @GET("message/messages/{messageId}")
    suspend fun getChatMessages(
        @Path("messageId") messageId: Int
    ) : Response<AllChatsResponse>
}