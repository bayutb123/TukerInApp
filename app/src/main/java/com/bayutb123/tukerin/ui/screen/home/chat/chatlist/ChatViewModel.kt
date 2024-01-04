package com.bayutb123.tukerin.ui.screen.home.chat.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.domain.usecase.ChatUseCase
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.RoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
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
    val chatListState : StateFlow<ChatListState> = _chatListState

    init {
        viewModelScope.launch {
            userId = dataStoreUseCase.getUserId()!!
        }
    }


    fun getChatList() {
        viewModelScope.launch {
            chatUseCase.getAllChats(userId)
            getAllChats()
        }
    }

    private fun getAllChats() {
        viewModelScope.launch {
            roomUseCase.getAllChats(userId).collect { result ->
                if (result.isNotEmpty()) {
                    Timber.d("Chat list: $result")
                    _chatListState.emit(ChatListState.Success(result))
                    result.forEach {
                        refreshAllMessages(it.id)
                    }
                } else {
                    _chatListState.emit(ChatListState.Empty("No chats found"))
                }
            }
        }
    }



    private fun refreshAllMessages(chatId: Int) {
        viewModelScope.launch {
            chatUseCase.getChatMessages(chatId)
        }
    }

}