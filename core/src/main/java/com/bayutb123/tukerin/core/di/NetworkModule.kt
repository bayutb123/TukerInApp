package com.bayutb123.tukerin.core.di

import android.content.Context
import com.bayutb123.tukerin.core.BuildConfig
import com.bayutb123.tukerin.core.okhttp.interceptor.ContentTypeInterceptor
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(context: Context) : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .cache(
                    Cache(
                        directory = File(context.cacheDir, "http-cache"),
                        maxSize = 10L * 1024L * 1024L // 10 MiB
                    )
                )
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(ContentTypeInterceptor())
                .addInterceptor(interceptor)
                .build()
        } else OkHttpClient.Builder()
            .cache(
                Cache(
                    directory = File(context.cacheDir, "http-cache"),
                    maxSize = 10L * 1024L * 1024L // 10 MiB
                )
            )
            .addInterceptor(ContentTypeInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BuildConfig.apiUrl + BuildConfig.pathUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }
}