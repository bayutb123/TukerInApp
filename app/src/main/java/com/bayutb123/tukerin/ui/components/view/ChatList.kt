package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.domain.model.Chat
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatList(
    data: List<Chat>,
    onItemClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Messages") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(items = data, key = { item -> item.id } ){ chat ->
                ListItem(
                    headlineContent = { Text(text = chat.receiver.name) },
                    overlineContent = {
                        Text(text = chat.context)
                    },
                    leadingContent = {
                        AsyncImage(
                            model = "",
                            contentDescription = "",
                            modifier = Modifier
                                .background(shape = CircleShape, color = Color.Transparent)
                                .size(50.dp),
                            contentScale = ContentScale.Crop
                        )
                    },
                    supportingContent = {
                        Text(text = chat.lastMessage!!.message)
                    },
                    modifier = Modifier.clickable {
                        onItemClick(chat.id)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessageList() {
    TukerInTheme {
        ChatList(
            data = listOf(
                Chat(
                    id = 1,
                    receiver = User(
                        id = 2,
                        name = "Bayu",
                        email = "",
                        token = "",
                        isPremium = false
                    ),
                    context = "Product",
                    lastMessage = Message(
                        id = 1,
                        chatId = 1,
                        senderId = 1,
                        receiverId = 2,
                        message = "Hello",
                        attachment = null,
                        isRead = false,
                        createdAt = ""
                    ),
                    userId = 1,
                    createdAt = "",
                )
            ),
            onItemClick = {}
        )
    }
}