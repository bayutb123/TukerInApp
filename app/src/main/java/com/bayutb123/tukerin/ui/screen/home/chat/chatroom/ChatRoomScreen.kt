package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.ui.components.view.ChatBubble
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    chatRoomViewModel: ChatRoomViewModel = hiltViewModel(),
    chatId: Int,
) {
    val listState = rememberLazyListState()
    chatRoomViewModel.getAllMessages(chatId)
    val state by chatRoomViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Nama Produk") })
        }
    ) {paddingValues ->  
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state) {
                is ChatRoomState.Success -> {
                    LazyColumn(state = listState) {
                        items(items = (state as ChatRoomState.Success).data, key = { item -> item.id }) { message ->
                            ChatBubble(message = message, userId = 1)
                        }
                    }

                    LaunchedEffect(listState) {
                        listState.scrollToItem(listState.layoutInfo.totalItemsCount - 1)
                    }
                }

                is ChatRoomState.Loading -> {
                    Text(text = "Loading")
                }

                else -> {
                    Text(text = (state as ChatRoomState.Failed).message)
                }
            }
        }
    }
}

@Preview
@Composable
fun ChatRoomPreview() { 
    TukerInTheme {
    }
}