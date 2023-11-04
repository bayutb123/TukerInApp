package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String) : NetworkResult<User>
    suspend fun register(name: String, email: String, password: String) : NetworkResult<User>
}