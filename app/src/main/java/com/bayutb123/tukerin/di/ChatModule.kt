package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.data.source.remote.service.ChatService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object ChatModule {
    @Provides
    fun provideChatService(retrofit: Retrofit) : ChatService {
        return retrofit.create(ChatService::class.java)
    }
}