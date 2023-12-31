package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.data.NetworkResult
import com.bayutb123.tukerin.domain.usecase.ChatUseCase
import com.bayutb123.tukerin.domain.usecase.RoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase,
    private val roomUseCase: RoomUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ChatRoomState>(ChatRoomState.Loading)
    val state = _state.asStateFlow()
    fun getAllMessages(chatId: Int) {
        _state.value = ChatRoomState.Loading
        viewModelScope.launch {
            when (val result = chatUseCase.getChatMessages(chatId)) {
                is NetworkResult.Success -> {
                    if (result.data!!.isNotEmpty()) {
                        _state.value = ChatRoomState.Success(result.data)
                    } else {
                        _state.value = ChatRoomState.Empty
                    }
                }

                is NetworkResult.Error -> {
                    getAllLocalMessages(chatId)
                    if (_state.value is ChatRoomState.Empty) {
                        _state.value = ChatRoomState.Failed("Failed to retrieve messages from server")
                    }
                }

                else -> {
                    getAllLocalMessages(chatId)
                    if (_state.value is ChatRoomState.Empty) {
                        _state.value = ChatRoomState.Empty
                    }
                }
            }
        }
    }

    private fun getAllLocalMessages(chatId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = roomUseCase.getAllMessage(chatId)
            if (result.isNotEmpty()) {
                _state.value = ChatRoomState.Success(result)
            } else {
                _state.value = ChatRoomState.Empty
            }
        }
    }
}