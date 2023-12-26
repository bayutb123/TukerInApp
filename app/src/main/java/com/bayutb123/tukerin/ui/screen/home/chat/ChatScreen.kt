package com.bayutb123.tukerin.ui.screen.home.chat

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.view.ChatList

@Composable
fun ChatScreen(
    modifier: Modifier = Modifier
) {
    val chatViewModel = hiltViewModel<ChatViewModel>()
    DisposableEffect(Unit) {
        chatViewModel.getAllChats()
        onDispose {

        }
    }
    val chatListState by chatViewModel.chatListState.collectAsState()

    Log.d("Test", chatViewModel.userId.toString())
    chatListState.let { state ->
        when (state) {
            is ChatListState.Success -> {
                Log.d("ChatList", state.chatList.toString())
                ChatList(data = state.chatList, onItemClick = {

                })
            }

            is ChatListState.Loading -> {

            }

            else -> {

            }
        }
    }
}