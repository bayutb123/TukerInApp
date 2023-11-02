package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.data.Resource
import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun getAllPosts(userId: Int) : Resource<Any> = postRepository.getAllPosts(userId)
}