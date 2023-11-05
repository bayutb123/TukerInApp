package com.bayutb123.tukerin.ui.screen.home.profile

import androidx.lifecycle.ViewModel
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {
    suspend fun logout() {
        dataStoreUseCase.clearUser()
    }
}