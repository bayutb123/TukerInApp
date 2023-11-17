package com.bayutb123.tukerin.data.source.remote.repository

import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {
    override suspend fun getAllPosts(userId: Int): NetworkResult<List<Post>> {
        return try {
            val response = apiService.getAllPosts(userId)
            if (response.isSuccessful) {
                NetworkResult.Success(DataMapper.mapPostResponseToPost(response.body()!!.posts!!))
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun searchPost(query: String, userId: Int): NetworkResult<List<Post>> {
        return try {
            val response = apiService.searchPost(query, userId)
            if (response.isSuccessful) {
                NetworkResult.Success(DataMapper.mapPostResponseToPost(response.body()!!.posts!!))
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun getSuggestions(query: String, userId: Int): NetworkResult<List<String>> {
        return try {
            val response = apiService.getSuggestions(query, userId)
            if (response.isSuccessful) {
                NetworkResult.Success(response.body()!!.suggestions.map {
                    it.title
                })
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun getPost(postId: Int): NetworkResult<Post> {
        return try {
            val response = apiService.getPost(postId)
            if (response.isSuccessful) {
                NetworkResult.Success(DataMapper.mapPostDetailResponseToPost(response.body()!!.post))
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }

}