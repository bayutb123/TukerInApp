package com.bayutb123.tukerin.ui.screen.home.dashboard

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import com.bayutb123.tukerin.ui.utils.Connection
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


    private val _state = MutableStateFlow<DashboardState>(DashboardState.Loading(emptyList()))
    val state = _state.asStateFlow()
    private val _searchState = MutableStateFlow<SearchState>(SearchState.Empty("Type something"))
    val searchState = _searchState.asStateFlow()
    private val _fetchState = MutableStateFlow(true)
    val fetchState = _fetchState.asStateFlow()
    private var currentPage = 1

    fun checkConnection(context: Context): Boolean {
        val connectionState = Connection(context).isConnected()
        Log.d("CONNECTION", connectionState.toString())
        if (!connectionState) {
            _state.value = DashboardState.Failed("No internet connection")
        }
        return connectionState
    }

    fun getAllPost(userId: Int, isReset: Boolean = false, context: Context) {
        var oldData = _state.value
        if (isReset) {
            currentPage = 1
            oldData = DashboardState.Loading(emptyList())
        }
        viewModelScope.launch {
            _fetchState.value = true
            delay(2000)
            when (val result = postUseCase.getAllPosts(userId, currentPage++)) {
                is NetworkResult.Success -> {
                    if (result.data?.isNotEmpty() == true) {
                        if (oldData is DashboardState.Success) {
                            _state.value = DashboardState.Success(oldData.data + result.data)
                        } else {
                            _state.value = DashboardState.Success(result.data)
                        }
                    } else {
                        Toast.makeText(
                            context,
                            "No more ad to show for now",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is NetworkResult.Error -> {
                    _state.value = DashboardState.Failed("Server error :( \nplease try again later")
                }

                else -> {
                    _state.value = DashboardState.Empty("No result")
                }
            }
            if (_state.value == DashboardState.Success(emptyList())) {
                _state.value = DashboardState.Empty("No result")
            }
            _fetchState.value = false
        }
    }

    fun searchPost(query: String, userId: Int) {
        viewModelScope.launch {
            _state.value = DashboardState.Loading(emptyList())
            when (val result = postUseCase.searchPost(query, userId)) {
                is NetworkResult.Success -> {
                    result.data?.let {
                        _state.value = DashboardState.Success(it)
                    }
                }

                is NetworkResult.Error -> {
                    _state.value = DashboardState.Failed("Error code ${result.message}")
                }

                else -> {
                    _state.value = DashboardState.Empty("No result")
                }
            }
        }
    }

    fun getSuggestion(userId: Int, query: String) {
        if (query == "") {
            _searchState.value = SearchState.Empty(
                "Type something,\nwe will generate suggestion for you"
            )
        } else {
            viewModelScope.launch {
                when (val result = postUseCase.getSuggestions(query, userId)) {
                    is NetworkResult.Success -> {
                        result.data?.let {
                            _searchState.value = SearchState.Success(it)

                            if (it.isEmpty()) {
                                _searchState.value =
                                    SearchState.Empty("Sorry, we can't find anything")
                            }
                        }
                    }

                    else -> {
                        _searchState.value = SearchState.Empty(

                            "Sorry, we land into a problem\nPlease try again later"
                        )
                    }
                }
            }
        }
    }

    fun setLoading() {
        _searchState.value = SearchState.Loading
    }
}
