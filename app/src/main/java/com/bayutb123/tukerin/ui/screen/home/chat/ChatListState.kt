package com.bayutb123.tukerin.ui.screen.home.chat

import com.bayutb123.tukerin.domain.model.Chat

sealed class ChatListState {
    data class Success(val chatList :List<Chat>) : ChatListState()
    data class Empty(val chatList: List<Chat>) : ChatListState()
    data class Failed(val message: String) : ChatListState()
    data class Loading(val chatList: List<Chat>?) : ChatListState()
}