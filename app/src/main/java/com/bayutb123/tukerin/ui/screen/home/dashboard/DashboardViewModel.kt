package com.bayutb123.tukerin.ui.screen.home.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.data.Resource
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
    private val _state = MutableStateFlow<DashboardState>(DashboardState.Loading(emptyList()))
    val state = _state.asStateFlow()
    private val _searchState = MutableStateFlow<SearchState>(SearchState.Loading)
    val searchState = _searchState.asStateFlow()

    fun getAllPost(userId: Int) {
        _state.value = DashboardState.Loading(emptyList())
        viewModelScope.launch {
            when(val result = postUseCase.getAllPosts(userId)){
                is NetworkResult.Success -> {
                    result.data?.let {
                        _state.value = DashboardState.Success(it)
                    }
                }
                is NetworkResult.Error -> {
                    _state.value = DashboardState.Failed(result.message!!)
                }
                else -> {
                    _state.value = DashboardState.Empty("No result")
                }
            }
            if (_state.value == DashboardState.Success(emptyList())) {
                _state.value = DashboardState.Empty("No result")
            }
        }
    }

    fun searchPost(query: String, userId: Int) {
        viewModelScope.launch {
            _state.value = DashboardState.Loading(emptyList())
            when(val result = postUseCase.searchPost(query, userId)){
                is Resource.Success -> {
                    _state.value = DashboardState.Success(result.result)
                    if (result.result.isEmpty()) {
                        _state.value = DashboardState.Empty("No result")
                    }
                }
                else -> {
//                    _state.value = DashboardState.Failed(
//                        when(result) {
//                            is Resource.Failed -> result.errorCode
//                            else -> 0
//                        }
//                    )
                }
            }
        }
    }

    fun getSuggestion(userId: Int, query: String) {
        viewModelScope.launch {
            when (val result = postUseCase.getSuggestions(query, userId)) {
                is Resource.Success -> {
                    _searchState.value = SearchState.Success(result.result)
                    // check if the result is empty
                    if (result.result.isEmpty()) {
                        _searchState.value = SearchState.Empty("No suggestion")
                    }
                }
                else -> {
                    _searchState.value = SearchState.Empty("No suggestion")
                }
            }
        }
    }

    fun setLoading() {
        _searchState.value = SearchState.Loading
    }
}