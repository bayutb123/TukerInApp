package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(email: String, password: String) : NetworkResult<User> {
        return authRepository.login(email, password)
    }

    suspend fun register(name: String, email: String, password: String) : NetworkResult<User> {
        return authRepository.register(name, email, password)
    }
}