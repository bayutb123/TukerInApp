package com.bayutb123.tukerin.ui.screen.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.Resource
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
    private val _searchState = MutableStateFlow<SearchState>(SearchState.Loading)
    val searchState = _searchState.asStateFlow()

    fun getAllPost(userId: Int) {
        _state.value = DashboardState.Loading
        viewModelScope.launch {
            when(val result = postUseCase.getAllPosts(userId)){
                is Resource.Success -> {
                    _state.value = DashboardState.Success(result.result as List<Post>)
                }
                is Resource.Failed -> {
                    _state.value = DashboardState.Failed(result.errorCode)
                }
                else -> {
                    _state.value = DashboardState.Empty
                }
            }
            if (_state.value == DashboardState.Success(emptyList())) {
                _state.value = DashboardState.Empty
            }
        }
    }

    fun searchPost(query: String, userId: Int) {
        viewModelScope.launch {
            when(val result = postUseCase.searchPost(query, userId)){
                is Resource.Success -> {
                    _state.value = DashboardState.Success(result.result as List<Post>)
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

    fun getSuggestion(userId: Int, query: String) {
        viewModelScope.launch {
            _searchState.value = SearchState.Loading
            delay(1000)
            when (val result = postUseCase.getSuggestions(query, userId)) {
                is Resource.Success -> {
                    _searchState.value = SearchState.Success(result.result)
                }
                else -> {
                    _searchState.value = SearchState.Empty("No suggestion")
                }
            }
        }
    }
}