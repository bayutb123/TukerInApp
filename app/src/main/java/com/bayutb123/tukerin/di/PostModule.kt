package com.bayutb123.tukerin.di

import com.bayutb123.tukerin.data.source.remote.service.AuthService
import com.bayutb123.tukerin.data.source.remote.repository.PostRepositoryImpl
import com.bayutb123.tukerin.data.source.remote.service.PostService
import com.bayutb123.tukerin.domain.repository.PostRepository
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    fun providePostService(retrofit: Retrofit) : PostService {
        return retrofit.create(PostService::class.java)
    }

    @Provides
    fun providePostRepository(postService: PostService) : PostRepository {
        return PostRepositoryImpl(postService)
    }

    @Provides
    fun providePostUseCase(postRepository: PostRepository) : PostUseCase {
        return PostUseCase(postRepository)
    }
}