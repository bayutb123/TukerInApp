package com.bayutb123.tukerin.data.source.remote.repository

import android.content.Context
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.core.utils.MediaUtils
import com.bayutb123.tukerin.data.source.remote.request.CreatePostRequest
import com.bayutb123.tukerin.data.source.remote.request.validate
import com.bayutb123.tukerin.data.source.remote.response.detail.toPost
import com.bayutb123.tukerin.data.source.remote.response.home.posts.toPostList
import com.bayutb123.tukerin.data.source.remote.response.home.suggestions.toSuggestionsList
import com.bayutb123.tukerin.data.source.remote.service.PostService
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.repository.PostRepository
import okio.IOException
import timber.log.Timber
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
                Timber.d(response.code().toString())
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            Timber.d(e.toString())
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
            Timber.d(e.toString())
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

    override suspend fun createPost(createPostRequest: CreatePostRequest, context: Context): NetworkResult<Int> {
        if (!createPostRequest.validate()) {
            return NetworkResult.Error(400)
        }
        val images = MediaUtils.preparePart(createPostRequest.images, context)

        try {
            val request = postService.createPost(
                createPostRequest.userId,
                createPostRequest.title.replace("\"", ""),
                createPostRequest.content.replace("\"", ""),
                images,
                createPostRequest.lat,
                createPostRequest.long,
                createPostRequest.price
            )

            return if (request.isSuccessful) {
                NetworkResult.Success(request.code())
            } else {
                Timber.d(request.message())
                NetworkResult.Error(request.code())
            }
        } catch (e: IOException) {
            return NetworkResult.Error(999)
        } catch (e: Exception) {
            return NetworkResult.Error(999)
        } finally {
            Timber.d("Clearing local cache")
            MediaUtils.clearLocalCache(context)
        }
    }

    override suspend fun getMyPosts(userId: Int, page: Int): NetworkResult<List<Post>> {
        return try {
            val response = postService.getMyPosts(userId, page)
            if (response.isSuccessful) {
                val posts = response.body()?.toPostList().orEmpty()
                NetworkResult.Success(posts)
            } else {
                Timber.d(response.code().toString())
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            Timber.d(e.toString())
            NetworkResult.Error(e.hashCode())
        }
    }

}
