package com.bayutb123.tukerin.ui.screen.home.chat.chatlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.bayutb123.tukerin.R
import com.bayutb123.tukerin.ui.components.view.ChatList
import com.bayutb123.tukerin.ui.screen.Screen

@Composable
fun ChatListScreen(
    onNavigationRequested: (String) -> Unit
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.chat_skeleton)
    )
    val chatViewModel = hiltViewModel<ChatViewModel>()
    DisposableEffect(Unit) {
        chatViewModel.getAllChats()
        onDispose {

        }
    }
    val chatListState by chatViewModel.chatListState.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {
            chatListState.let { state ->
                when (state) {
                    is ChatListState.Success -> {
                        ChatList(data = state.chatList) {
                            onNavigationRequested(Screen.ChatRoom.route + "/${it}")
                        }
                    }

                    is ChatListState.Loading -> {
                        LazyColumn {
                            items(10) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    LottieAnimation(
                                        iterations = LottieConstants.IterateForever,
                                        alignment = Alignment.CenterStart,
                                        composition = composition,
                                        modifier = Modifier
                                            .height(72.dp)
                                            .padding(horizontal = 16.dp)
                                    )
                                }
                            }
                        }
                    }

                    else -> {

                        Text(text = (state as ChatListState.Failed).message)

                    }
                }
            }

        }
    }
}

