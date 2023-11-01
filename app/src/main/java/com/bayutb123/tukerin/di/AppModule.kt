package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.BuildConfig
import com.bayutb123.tukerin.data.repository.AuthRepositoryImpl
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.domain.repository.AuthRepository
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor() { chain ->
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
        println(BuildConfig.apiUrl + BuildConfig.pathUrl)
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
}