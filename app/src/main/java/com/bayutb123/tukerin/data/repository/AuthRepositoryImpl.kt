package com.bayutb123.tukerin.data.repository

import android.util.Log
import com.bayutb123.tukerin.data.DataMapper
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.ApiService
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun login(email: String, password: String): NetworkResult<User> {
        return try {
            val result = apiService.login(email, password)
            if (result.isSuccessful) {
                when (result.code()) {
                    200 -> {
                        val body = result.body()
                        NetworkResult.Success(DataMapper.mapLoginUserToUser(body?.user!!))
                    }
                    else -> {
                        NetworkResult.Error(result.code())
                    }
                }
            } else {
                NetworkResult.Error(result.code())
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "login: ${e.message}", e)
            NetworkResult.Error(e.hashCode())
        }
    }

    override suspend fun register(name: String, email: String, password: String): NetworkResult<User> {
        return try {
            val result = apiService.register(name, email, password)
            if (result.isSuccessful) {
                when (result.code()) {
                    201 -> {
                        // success
                        NetworkResult.Success(DataMapper.mapRegisterUserToUser(result.body()?.user!!))
                    }
                    else -> {
                        NetworkResult.Error(result.code())
                    }
                }
            } else {
                NetworkResult.Error(result.code())
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "register: ${e.message}", e)
            NetworkResult.Error(e.hashCode())
        }
    }

}