package com.bayutb123.tukerin.domain.repository

import com.bayutb123.tukerin.domain.model.User

interface DataStoreRepository {

    suspend fun saveUser(user: User)
    suspend fun clearUser()
    suspend fun getToken(): String?
    suspend fun getUserId(): Int?

}