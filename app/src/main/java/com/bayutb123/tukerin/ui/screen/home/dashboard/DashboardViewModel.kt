package com.bayutb123.tukerin.ui.screen.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.Resource
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val postUseCase: PostUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val state = _state.asStateFlow()

    @Suppress("UNCHECKED_CAST")
    fun getAllPost(userId: Int) {
        _state.value = DashboardState.Loading
        viewModelScope.launch {
            when(val result = postUseCase.getAllPosts(userId)){
                is Resource.Success -> {
                    _state.value = DashboardState.Success<Post>(result.result as List<Post>)
                }
                is Resource.Failed -> {
                    _state.value = DashboardState.Failed(result.errorCode)
                }
                else -> {
                    _state.value = DashboardState.Empty
                }
            }
            if (_state.value == DashboardState.Success<Post>(emptyList())) {
                _state.value = DashboardState.Empty
            }
        }
    }

    fun searchPost(query: String, userId: Int) {
        viewModelScope.launch {
            when(val result = postUseCase.searchPost(query, userId)){
                is Resource.Success -> {
                    _state.value = DashboardState.Success(result.result as List<*>)
                }
                is Resource.Failed -> {
                    _state.value = DashboardState.Failed(result.errorCode)
                }
                else -> {
                    _state.value = DashboardState.Empty
                }
            }
        }
    }
}