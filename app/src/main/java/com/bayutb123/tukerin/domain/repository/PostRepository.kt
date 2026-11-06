package com.bayutb123.tukerin.domain.repository

import android.content.Context
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.request.CreatePostRequest
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.model.PostCategory

interface PostRepository {
    suspend fun getAllPosts(userId: Int, page: Int) : NetworkResult<List<Post>>
    suspend fun searchPost(query: String, userId: Int) : NetworkResult<List<Post>>
    suspend fun getSuggestions(query: String, userId: Int) : NetworkResult<List<String>>
    suspend fun getPost(postId: Int) : NetworkResult<Post>
    suspend fun createPost(createPostRequest: CreatePostRequest, context: Context) : NetworkResult<Int>
    suspend fun getMyPosts(userId: Int, page: Int) : NetworkResult<List<Post>>
    suspend fun getPostCategories() : NetworkResult<List<PostCategory>>
    suspend fun getPostSubCategory(categoryId: Int) : NetworkResult<List<PostCategory>>
    suspend fun deletePost(postId: Int) : NetworkResult<Int>
    suspend fun getActivePosts(userId: Int) : NetworkResult<List<Post>>
    suspend fun updatePostPublishStatus(postId: Int, statusId : Int) : NetworkResult<Int>
    suspend fun postReview(postId: Int, review: String, rating: Int) : NetworkResult<Int>

}