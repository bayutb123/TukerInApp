package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.data.source.remote.repository.AuthRepositoryImpl
import com.bayutb123.tukerin.data.source.remote.service.AuthService
import com.bayutb123.tukerin.domain.repository.AuthRepository
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideAuthService(retrofit: Retrofit) : AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    fun provideAuthRepository(authService : AuthService) : AuthRepository {
        return AuthRepositoryImpl(authService)
    }

    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository) : AuthUseCase {
        return AuthUseCase(authRepository)
    }
}