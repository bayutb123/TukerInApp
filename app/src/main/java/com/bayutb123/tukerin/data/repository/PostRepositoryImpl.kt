package com.bayutb123.tukerin.data.repository

import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.Resource
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : PostRepository {
    @Suppress("UNCHECKED_CAST")
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
}