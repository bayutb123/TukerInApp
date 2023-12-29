package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import com.bayutb123.tukerin.domain.model.Message

sealed class ChatRoomState {
    object Loading : ChatRoomState()
    data class Success(val data: List<Message>) : ChatRoomState()
    data class Failed(val message: String) : ChatRoomState()
    object Empty : ChatRoomState()

}