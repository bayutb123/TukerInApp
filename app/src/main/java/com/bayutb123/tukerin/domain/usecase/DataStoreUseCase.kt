package com.bayutb123.tukerin.domain.usecase

import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.DataStoreRepository
import javax.inject.Inject

class DataStoreUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {
        suspend fun saveUser(user: User) {
            dataStoreRepository.saveUser(user)
        }

        suspend fun clearUser() {
            dataStoreRepository.clearUser()
        }

        suspend fun getToken(): String? {
            return dataStoreRepository.getToken()
        }

        suspend fun getUserId(): Int? {
            return dataStoreRepository.getUserId()
        }

}