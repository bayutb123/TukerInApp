package com.bayutb123.tukerin.ui.screen.home.chat

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.usecase.ChatUseCase
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.ui.utils.Connection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val dataStoreUseCase: DataStoreUseCase
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

    fun checkConnection(context: Context) : Boolean {
        val connectionInfo = Connection(context).isConnected()
        if (!connectionInfo) {
            _chatListState.value = ChatListState.Failed("No Internet Connection")
        }
        return connectionInfo
    }

    fun getAllChats() {
        _chatListState.value = ChatListState.Loading(emptyList())
        viewModelScope.launch {
            when (val result = chatUseCase.getAllChats(userId)) {
                is NetworkResult.Success -> {
                    if (result.data?.isNotEmpty() == true) {
                        _chatListState.value = ChatListState.Success(result.data)
                    } else {
                        _chatListState.value = ChatListState.Empty(emptyList())
                    }
                }
                else -> {
                    _chatListState.value = ChatListState.Failed("Gagal mendapatkan data :(")
                }
            }
        }
    }
}