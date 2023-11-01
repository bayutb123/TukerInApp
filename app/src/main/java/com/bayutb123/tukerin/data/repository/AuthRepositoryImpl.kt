package com.bayutb123.tukerin.data.repository

import android.util.Log
import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.data.source.remote.NetworkResult
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): User? {
        return try {
            val result = apiService.login(email, password)
            if (result.code() == 200) {
                val user = DataMapper.convertLoginUserToUser(result.body()?.user!!)
                user
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "login: ${e.message}")
            null
        }
    }

}