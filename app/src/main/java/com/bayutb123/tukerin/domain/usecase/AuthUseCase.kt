package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.repository.AuthRepository
import javax.inject.Inject

class AuthUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun login(name: String, password: String) {
        authRepository.login(name, password)
    }
}