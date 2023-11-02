package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.data.Resource

interface PostRepository {
    suspend fun getAllPosts(userId: Int) : Resource<Any>
}