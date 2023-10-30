package com.bayutb123.tukerin.data.repository

import com.bayutb123.tukerin.domain.repository.AuthRepository

class AuthRepositoryImpl : AuthRepository {
    override suspend fun login(name: String, password: String) {
        println("Logged in")
    }

}