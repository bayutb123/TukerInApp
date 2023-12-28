package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import androidx.lifecycle.ViewModel
import com.bayutb123.tukerin.domain.usecase.ChatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatRoomViewModel @Inject constructor(
    private val chatUseCase: ChatUseCase
) : ViewModel() {


}