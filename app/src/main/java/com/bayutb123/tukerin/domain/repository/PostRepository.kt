package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.model.Post

interface PostRepository {
    suspend fun getAllPosts(userId: Int, page: Int) : NetworkResult<List<Post>>
    suspend fun searchPost(query: String, userId: Int) : NetworkResult<List<Post>>
    suspend fun getSuggestions(query: String, userId: Int) : NetworkResult<List<String>>
    suspend fun getPost(postId: Int) : NetworkResult<Post>
}