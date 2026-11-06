package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.model.UserRating

interface UserRepository {
    suspend fun getUserProfile(id: Int) : NetworkResult<User>
    suspend fun getUserRating(id: Int) : NetworkResult<UserRating>
}