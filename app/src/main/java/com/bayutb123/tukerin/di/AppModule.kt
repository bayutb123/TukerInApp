package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.data.source.remote.repository.AuthRepositoryImpl
import com.bayutb123.tukerin.data.source.remote.repository.PostRepositoryImpl
import com.bayutb123.tukerin.domain.repository.AuthRepository
import com.bayutb123.tukerin.domain.repository.PostRepository
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import com.bayutb123.tukerin.domain.usecase.PostUseCase
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
object AppModule {

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()
                    chain.proceed(request)
                }.build()
        } else OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .build()
                chain.proceed(request)
            }.build()
    }

    @Provides
    fun provideApiService(okHttpClient: OkHttpClient) : ApiService {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.apiUrl + BuildConfig.pathUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    fun provideAuthRepository(apiService : ApiService) : AuthRepository {
        return AuthRepositoryImpl(apiService)
    }

    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository) : AuthUseCase {
        return AuthUseCase(authRepository)
    }

    @Provides
    fun providePostRepository(apiService: ApiService) : PostRepository {
        return PostRepositoryImpl(apiService)
    }

    @Provides
    fun providePostUseCase(postRepository: PostRepository) : PostUseCase {
        return PostUseCase(postRepository)
    }
}