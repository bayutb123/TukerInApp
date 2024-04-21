package com.bayutb123.tukerin.ui.screen.home.myads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyAdsViewModel @Inject constructor(
    private val postUseCase: PostUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MyAdsState> = MutableStateFlow(MyAdsState.Loading)
    private val _bottomSheetState: MutableStateFlow<BottomSheetState> = MutableStateFlow(BottomSheetState.HIDE)
    val bottomSheetState: StateFlow<BottomSheetState> = _bottomSheetState.asStateFlow()
    val state: StateFlow<MyAdsState> = _state.asStateFlow()

    fun getMyAds() {
        viewModelScope.launch {
            _state.value = MyAdsState.Loading
            delay(500)
            val id = dataStoreUseCase.getUserId()
            if (id != null) {
                when (val result = postUseCase.getMyPosts(id, 1) ){
                    is NetworkResult.Success -> {
                        if (result.data != null) {
                            _state.value = MyAdsState.Success(result.data as List<Post>)
                        }
                    }
                    is NetworkResult.Error -> {
                        _state.value = MyAdsState.Error("Error: ${result.message}")
                    }
                    is NetworkResult.Loading -> {
                        _state.value = MyAdsState.Loading
                    }
                    else -> {
                        _state.value = MyAdsState.Error("App Error")
                    }
                }
            }
        }
    }

    fun deletePost(postId: Int) {
        viewModelScope.launch {
            _bottomSheetState.value = BottomSheetState.LOADING
            delay(500)
            when (val result = postUseCase.deletePost(postId)) {
                is NetworkResult.Success -> {
                    getMyAds()
                }
                is NetworkResult.Error -> {
                    _bottomSheetState.value = BottomSheetState.HIDE
                    _state.value = MyAdsState.Error("Error: ${result.message}")
                }
                else -> {
                    _bottomSheetState.value = BottomSheetState.HIDE
                    _state.value = MyAdsState.Error("App Error")
                }
            }
        }
    }

    fun bottomSheet(state : BottomSheetState) {
        _bottomSheetState.value = state
    }

}