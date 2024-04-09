package com.bayutb123.tukerin.data.source.remote.repository

import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.data.source.remote.response.ResponseCode.NOT_FOUND
import com.bayutb123.tukerin.data.source.remote.service.UserService
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.UserRepository
import toModel
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userService: UserService
) : UserRepository {

    override suspend fun getUserProfile(id: Int) : NetworkResult<User> {
        return try {
            val response = userService.getUserProfile(id)
            if (response.isSuccessful) {
                if (response.body() != null) {
                    NetworkResult.Success(response.body()!!.toModel())
                } else {
                    NetworkResult.Error(NOT_FOUND)
                }
            } else {
                NetworkResult.Error(response.code())
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.hashCode())
        }
    }
}