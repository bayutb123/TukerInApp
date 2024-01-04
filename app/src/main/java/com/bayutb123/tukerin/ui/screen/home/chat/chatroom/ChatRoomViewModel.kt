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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
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
            roomUseCase.getAllMessage(chatId).collect { result ->
                if (result.isNotEmpty()) {
                    _state.value = ChatRoomState.Success(result)
                } else {
                    _state.value = ChatRoomState.Empty
                }
            }
            chatUseCase.getChatMessages(chatId)
        }
    }
}