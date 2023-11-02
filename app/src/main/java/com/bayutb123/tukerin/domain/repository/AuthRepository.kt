package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.data.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String) : Resource<Any>
    suspend fun register(name: String, email: String, password: String) : Resource<Any>
}