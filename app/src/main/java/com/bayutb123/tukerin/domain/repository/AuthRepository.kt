package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.data.repository.Resource
import com.bayutb123.tukerin.data.source.remote.NetworkResult
import com.bayutb123.tukerin.domain.model.User

interface AuthRepository {
    suspend fun login(email: String, password: String) : User?
}