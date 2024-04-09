package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.domain.model.User

interface UserRepository {
    suspend fun getUserProfile(id: Int) : NetworkResult<User>
}