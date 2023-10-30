package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.data.repository.AuthRepositoryImpl
import com.bayutb123.tukerin.domain.repository.AuthRepository
import com.bayutb123.tukerin.domain.usecase.AuthUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAuthRepository() : AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    fun provideAuthUseCase(authRepository: AuthRepository) : AuthUseCase {
        return AuthUseCase(authRepository)
    }
}