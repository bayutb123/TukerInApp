package com.bayutb123.tukerin.data.source.remote.repository

import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.response.detail.toPost
import com.bayutb123.tukerin.data.source.remote.response.home.posts.toPostList
import com.bayutb123.tukerin.data.source.remote.response.home.suggestions.toSuggestionsList
import com.bayutb123.tukerin.data.source.remote.service.PostService
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postService: PostService
) : PostRepository {

    override suspend fun getAllPosts(userId: Int, page: Int): NetworkResult<List<Post>> {
        return try {
            val response = postService.getAllPosts(userId, page)
            if (response.isSuccessful) {
                val posts = response.body()?.toPostList().orEmpty()
                NetworkResult.Success(posts)
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun searchPost(query: String, userId: Int): NetworkResult<List<Post>> {
        return try {
            val response = postService.searchPost(query, userId)
            if (response.isSuccessful) {
                val posts = response.body()?.toPostList().orEmpty()
                NetworkResult.Success(posts)
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun getSuggestions(query: String, userId: Int): NetworkResult<List<String>> {
        return try {
            val response = postService.getSuggestions(query, userId)
            if (response.isSuccessful) {
                val suggestions = response.body()?.toSuggestionsList().orEmpty()
                NetworkResult.Success(suggestions)
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun getPost(postId: Int): NetworkResult<Post> {
        return try {
            val response = postService.getPost(postId)
            if (response.isSuccessful) {
                val post = response.body()!!
                NetworkResult.Success(post.toPost())
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }
}
