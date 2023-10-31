package com.bayutb123.tukerin.data.repository

import android.util.Log
import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject  constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String) : User? {
        return try {
            val result = apiService.login(email, password)
            Log.d("AuthRepositoryImpl", "login: ${result.message}")
            if (result.user != null) {
                DataMapper.convertLoginUserToUser(result.user)
            } else {
                null
            }
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }

}