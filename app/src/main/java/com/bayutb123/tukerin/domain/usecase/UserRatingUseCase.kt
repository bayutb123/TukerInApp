package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.repository.UserRepository
import javax.inject.Inject

class UserRatingUseCase @Inject constructor(
    val repository: UserRepository
) {
    suspend operator fun invoke(userId: Int) = repository.getUserRating(userId)
}