package com.bayutb123.tukerin.ui.screen.home.chat.chatlist

import com.bayutb123.tukerin.domain.model.Chat

sealed class ChatListState(
    var chatList: List<Chat>
) {
    data class Success(var list :List<Chat>) : ChatListState(list)
    data class Empty(val message: String) : ChatListState(emptyList())
    data class Failed(val message: String) : ChatListState(emptyList())
    data class Loading(var list: List<Chat>?) : ChatListState(list ?: emptyList())
}