package com.bayutb123.tukerin.ui.screen.home.chat.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.usecase.ChatUseCase
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.RoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val dataStoreUseCase: DataStoreUseCase,
    private val roomUseCase: RoomUseCase
) : ViewModel() {
    var userId = 0
    private val _chatListState = MutableStateFlow<ChatListState>(
        ChatListState.Loading(
            emptyList()
        )
    )
    val chatListState = _chatListState.asStateFlow()

    init {
        viewModelScope.launch {
            userId = dataStoreUseCase.getUserId()!!
        }
    }

    fun getAllChats() {
        _chatListState.value = ChatListState.Loading(emptyList())
        viewModelScope.launch {
            when (val result = chatUseCase.getAllChats(userId)) {
                is NetworkResult.Success -> {
                    if (result.data?.isNotEmpty() == true) {
                        _chatListState.value = ChatListState.Success(result.data)
                    } else {
                        _chatListState.value = ChatListState.Empty("No chats")
                    }
                }
                is NetworkResult.Error -> {
                    getAllLocalChats()
                    if (_chatListState.value is ChatListState.Empty) {
                        _chatListState.value = ChatListState.Failed("Failed to get chats")
                    }
                }
                else -> {
                    getAllLocalChats()
                    if (_chatListState.value is ChatListState.Empty) {
                        _chatListState.value = ChatListState.Empty("No chats")
                    }
                }
            }
        }
    }

    private fun getAllLocalChats() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = roomUseCase.getAllChats(userId)
            if (result.isNotEmpty()) {
                _chatListState.value = ChatListState.Success(result)
            } else {
                _chatListState.value = ChatListState.Empty("No chats")
            }
        }
    }
}