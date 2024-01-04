package com.bayutb123.tukerin.ui.screen.home.chat.chatlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bayutb123.tukerin.domain.usecase.ChatUseCase
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import com.bayutb123.tukerin.domain.usecase.RoomUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectIndexed
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
            chatUseCase.getAllChats(userId)

            getAllChats()
        }
    }

    fun getAllChats() {
        viewModelScope.launch {
            roomUseCase.getAllChats(userId).collect { result ->
                if (result.isNotEmpty()) {
                    _chatListState.value = ChatListState.Success(result)
                    result.forEach {
                        refreshAllMessages(it.id)
                    }
                } else {
                    _chatListState.value = ChatListState.Empty("No chats")
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