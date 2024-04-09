package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.repository.UserRepository
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend fun getUserProfile(id: Int) = userRepository.getUserProfile(id)
}