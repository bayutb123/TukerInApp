package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.data.source.remote.repository.UserRepositoryImpl
import com.bayutb123.tukerin.data.source.remote.service.UserService
import com.bayutb123.tukerin.domain.repository.UserRepository
import com.bayutb123.tukerin.domain.usecase.UserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object UserModule {

    @Provides
    fun provideUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
    @Provides
    fun provideUserRepository(userService: UserService): UserRepository {
        return UserRepositoryImpl(userService)
    }
    @Provides
    fun provideUserUseCase(userRepository: UserRepository): UserUseCase {
        return UserUseCase(userRepository)
    }

}