package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.ui.components.view.ChatBubble
import com.bayutb123.tukerin.ui.components.view.ChatList
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    modifier: Modifier = Modifier,
    chatRoomViewModel: ChatRoomViewModel = hiltViewModel(),
    chatId: Int,
) {
    val listMessage = listOf<Message>(
        Message(
            id = 1,
            chatId = 1,
            senderId = 1,
            message = "Halo",
            isRead = true,
            attachment = null,
            receiverId = 2,
        ),
        Message(
            id = 2,
            chatId = 1,
            senderId = 2,
            message = "Halo juga",
            isRead = true,
            attachment = null,
            receiverId = 1,
        ),
        Message(
            id = 3,
            chatId = 1,
            senderId = 1,
            message = "Apa kabar?",
            isRead = true,
            attachment = null,
            receiverId = 2,
        ),
        Message(
            id = 4,
            chatId = 1,
            senderId = 2,
            message = "Baik",
            isRead = true,
            attachment = null,
            receiverId = 1,
        ),
        Message(
            id = 5,
            chatId = 1,
            senderId = 1,
            message = "Saya mau tanya",
            isRead = true,
            attachment = null,
            receiverId = 2,
        ),
        Message(
            id = 6,
            chatId = 1,
            senderId = 2,
            message = "Apa?",
            isRead = true,
            attachment = null,
            receiverId = 1,
        ),
        Message(
            id = 7,
            chatId = 1,
            senderId = 1,
            message = "Saya mau tanya",
            isRead = true,
            attachment = null,
            receiverId = 2,
        ),
        Message(
            id = 8,
            chatId = 1,
            senderId = 2,
            message = "Apa?",
            isRead = true,
            attachment = null,
            receiverId = 1,
        ),
    )
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Nama Produk") })
        }
    ) {paddingValues ->  
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyColumn() {
                items(listMessage) {
                    ChatBubble(
                        message = it.message,
                        isSender = it.senderId == 1,
                        isRead = it.isRead,
                        time = "10:21 PM"
                    )
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