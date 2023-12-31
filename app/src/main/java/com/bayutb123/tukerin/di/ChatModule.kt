package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.data.source.local.dao.TukerInDao
import com.bayutb123.tukerin.data.source.remote.repository.ChatRepositoryImpl
import com.bayutb123.tukerin.data.source.remote.service.ChatService
import com.bayutb123.tukerin.domain.repository.ChatRepository
import com.bayutb123.tukerin.domain.usecase.ChatUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    fun provideChatService(retrofit: Retrofit) : ChatService {
        return retrofit.create(ChatService::class.java)
    }

    @Provides
    fun provideChatRepository(chatService: ChatService, tukerInDao: TukerInDao) : ChatRepository {
        return ChatRepositoryImpl(chatService, tukerInDao)
    }

    @Provides
    fun provideChatUseCase(chatRepository: ChatRepository) : ChatUseCase {
        return ChatUseCase(chatRepository)
    }

}