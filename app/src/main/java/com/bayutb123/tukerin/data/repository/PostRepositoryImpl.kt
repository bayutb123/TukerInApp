package com.bayutb123.tukerin.data.repository

import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.Resource
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {
    override suspend fun getAllPosts(userId: Int): Resource<List<Post>> {
        return try {
            val response = apiService.getAllPosts(userId)
            if (response.isSuccessful) {
                Resource.Success(DataMapper.mapPostResponseToPost(response.body()!!.posts!!))
            } else {
                Resource.Failed(response.code()) as Resource<List<Post>>
            }
        } catch (e: Exception) {
            Resource.Exception(e) as Resource<List<Post>>
        }
    }

    override suspend fun searchPost(query: String, userId: Int): Resource<List<Post>> {
        return try {
            val response = apiService.searchPost(query, userId)
            if (response.isSuccessful) {
                Resource.Success(DataMapper.mapPostResponseToPost(response.body()!!.posts!!))
            } else {
                Resource.Failed(response.code()) as Resource<List<Post>>
            }
        } catch (e: Exception) {
            Resource.Exception(e) as Resource<List<Post>>
        }
    }

    override suspend fun getSuggestions(query: String, userId: Int): Resource<List<String>> {
        return try {
            val response = apiService.getSuggestions(query, userId)
            if (response.isSuccessful) {
                Resource.Success(response.body()!!.suggestions.map { it.title })
            } else {
                Resource.Failed(response.code()) as Resource<List<String>>
            }
        } catch (e: Exception) {
            Resource.Exception(e) as Resource<List<String>>
        }
    }
}