package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.data.Resource
import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun getAllPosts(userId: Int) : Resource<Any> = postRepository.getAllPosts(userId)

    suspend fun searchPost(query: String, userId: Int) : Resource<Any> = postRepository.searchPost(query, userId)

    suspend fun getSuggestions(query: String, userId: Int) : Resource<List<String>> = postRepository.getSuggestions(query, userId)
}