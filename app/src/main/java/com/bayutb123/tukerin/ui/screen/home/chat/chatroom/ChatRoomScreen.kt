package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.input.CustomMessageField
import com.bayutb123.tukerin.ui.components.view.ChatBubble
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    chatRoomViewModel: ChatRoomViewModel = hiltViewModel(),
    chatId: Int,
) {
    val context = LocalContext.current
    val listState = rememberLazyListState()
    chatRoomViewModel.getAllMessages(chatId)
    val state by chatRoomViewModel.state.collectAsState()
    var message by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Nama Produk") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            })
        },
        bottomBar = {
            CustomMessageField(onTextChanged = {message = it}, singleLine = false, maxLines = 3, placeholder = "Ketik pesan disini" ) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                message = ""
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues),
            contentAlignment = androidx.compose.ui.Alignment.BottomCenter
        ) {
            when (state) {
                is ChatRoomState.Success -> {
                    Column {
                        LazyColumn(state = listState) {
                            items(
                                items = (state as ChatRoomState.Success).data,
                                key = { item -> item.id }) { message ->
                                ChatBubble(message = message, userId = 1)
                            }
                        }
                    }
                    LaunchedEffect(listState) {
                        listState.scrollToItem(listState.layoutInfo.totalItemsCount - 1)
                    }
                }

                is ChatRoomState.Loading -> {

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