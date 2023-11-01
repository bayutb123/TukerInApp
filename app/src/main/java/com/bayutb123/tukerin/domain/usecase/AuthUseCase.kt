package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.data.repository.Resource
import com.bayutb123.tukerin.data.source.remote.NetworkResult
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(email: String, password: String) : User? {
        return authRepository.login(email, password)
    }
}