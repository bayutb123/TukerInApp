package com.bayutb123.tukerin.domain.usecase

import android.content.Context
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.request.CreatePostRequest
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend fun getAllPosts(userId: Int, page: Int) : NetworkResult<List<Post>> = postRepository.getAllPosts(userId, page)

    suspend fun searchPost(query: String, userId: Int) : NetworkResult<List<Post>> = postRepository.searchPost(query, userId)

    suspend fun getSuggestions(query: String, userId: Int) : NetworkResult<List<String>> = postRepository.getSuggestions(query, userId)

    suspend fun getPost(postId: Int) : NetworkResult<Post> = postRepository.getPost(postId)
    suspend fun createPost(createPostRequest: CreatePostRequest, context: Context) = postRepository.createPost(createPostRequest, context)
    suspend fun getMyPosts(userId: Int, page: Int) : NetworkResult<List<Post>> = postRepository.getMyPosts(userId, page)
}