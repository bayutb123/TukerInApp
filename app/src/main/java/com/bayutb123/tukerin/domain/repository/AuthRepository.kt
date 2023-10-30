package com.bayutb123.tukerin.domain.repository

interface AuthRepository {

    suspend fun login(name: String, password: String)
}