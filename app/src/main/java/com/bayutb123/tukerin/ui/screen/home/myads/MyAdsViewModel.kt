package com.bayutb123.tukerin.ui.screen.home.myads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.core.data.NetworkResult
import com.bayutb123.tukerin.core.utils.PublishStatus
import com.bayutb123.tukerin.domain.model.Post
import com.bayutb123.tukerin.domain.model.UserRating
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.PostUseCase
import com.bayutb123.tukerin.domain.usecase.UserRatingUseCase
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
    private val userRatingUseCase: UserRatingUseCase,
    private val dataStoreUseCase: DataStoreUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<MyAdsState> = MutableStateFlow(MyAdsState.Loading)
    private val _activePostState: MutableStateFlow<MyAdsState> =
        MutableStateFlow(MyAdsState.Loading)
    private val _bottomSheetState: MutableStateFlow<BottomSheetState> =
        MutableStateFlow(BottomSheetState.HIDE)
    private val _sellerState: MutableStateFlow<SellerState> = MutableStateFlow(SellerState.Loading)
    val bottomSheetState: StateFlow<BottomSheetState> = _bottomSheetState.asStateFlow()
    val state: StateFlow<MyAdsState> = _state.asStateFlow()
    val activePostState: StateFlow<MyAdsState> = _activePostState.asStateFlow()
    val sellerState: StateFlow<SellerState> = _sellerState.asStateFlow()

    fun getMyAds(tabIndex: TabIndex) {
        viewModelScope.launch {
            _state.value = MyAdsState.Loading
            _activePostState.value = MyAdsState.Loading
            delay(500)
            val id = dataStoreUseCase.getUserId()
            if (id != null) {
                if (tabIndex == TabIndex.MY_ADS) {
                    when (val result = postUseCase.getMyPosts(id, 1)) {
                        is NetworkResult.Success -> {
                            if (result.data != null) {
                                val data = result.data as List<Post>
                                if (data.isNotEmpty()) {
                                    _state.value = MyAdsState.Success(data)
                                } else {
                                    _state.value = MyAdsState.Empty("No My Ads")
                                }
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
                } else if (tabIndex == TabIndex.ACTIVE_ADS) {
                    when (val result = postUseCase.getActivePosts(id)) {
                        is NetworkResult.Success -> {
                            if (result.data != null) {
                                val data = result.data as List<Post>
                                if (data.isNotEmpty()) {
                                    data.filter { it.status == PublishStatus.TRANSACTION_PENDING.name }
                                    _activePostState.value = MyAdsState.Success(data)
                                } else {
                                    _activePostState.value =
                                        MyAdsState.Empty("No Active Transaction")
                                }
                            }
                        }

                        is NetworkResult.Error -> {
                            _activePostState.value = MyAdsState.Error("Error: ${result.message}")
                        }

                        is NetworkResult.Loading -> {
                            _activePostState.value = MyAdsState.Loading
                        }

                        else -> {
                            _activePostState.value = MyAdsState.Error("App Error")
                        }
                    }
                }
            }
        }
    }

    fun deletePost(postId: Int, tabIndex: TabIndex) {
        viewModelScope.launch {
            _bottomSheetState.value = BottomSheetState.LOADING
            delay(500)
            when (val result = postUseCase.deletePost(postId)) {
                is NetworkResult.Success -> {
                    getMyAds(tabIndex = tabIndex)
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

    fun getSellerRating(sellerId: Int) {
        viewModelScope.launch {
            _sellerState.value = SellerState.Loading
            delay(500)

            when (val result = userRatingUseCase.invoke(sellerId)) {
                is NetworkResult.Success -> {
                    if (result.data != null) {
                        _sellerState.value = SellerState.Seller(result.data as UserRating)
                    }
                }

                is NetworkResult.Error -> {
                    _sellerState.value = SellerState.Error("Error: ${result.message}")
                }

                else -> {
                    _sellerState.value = SellerState.Error("App Error")
                }
            }
        }
    }

    fun postReview(postId: Int, review: String, rating: Int) {
        viewModelScope.launch {
            _bottomSheetState.value = BottomSheetState.LOADING
            delay(500)
            when (val result = postUseCase.postReview(postId, review, rating)) {
                is NetworkResult.Success -> {
                    finishTransaction(postId)
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

    private fun finishTransaction(postId: Int) {
        viewModelScope.launch {
            val status = PublishStatus.CLOSED
            _bottomSheetState.value = BottomSheetState.LOADING
            delay(500)
            when (val result = postUseCase.updatePostPublishStatus(postId, status.ordinal)) {
                is NetworkResult.Success -> {
                    getMyAds(TabIndex.ACTIVE_ADS)
                    _bottomSheetState.value = BottomSheetState.HIDE
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

    fun bottomSheet(state: BottomSheetState) {
        _bottomSheetState.value = state
    }

}