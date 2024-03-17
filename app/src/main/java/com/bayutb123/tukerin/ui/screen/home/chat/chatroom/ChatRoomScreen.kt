package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import android.view.ViewTreeObserver
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.input.CustomMessageField
import com.bayutb123.tukerin.ui.components.view.ChatBubble
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    chatRoomViewModel: ChatRoomViewModel = hiltViewModel(),
    chatId: Int,
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    chatRoomViewModel.getAllMessages(chatId)
    val state by chatRoomViewModel.state.collectAsState()
    var message by rememberSaveable {
        mutableStateOf("")
    }
    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
            // ... do anything you want here with `isKeyboardOpen`
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Nama Produk") }, actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
                }
            })
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.weight(1f)) {
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

                    is ChatRoomState.Empty -> {
                        Text(text = "Empty")
                    }

                    else -> {
                        Text(text = (state as ChatRoomState.Failed).message)
                    }
                }
            }
            CustomMessageField(
                onTextChanged = { message = it },
                singleLine = false,
                maxLines = 3,
                placeholder = "Ketik pesan disini",
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .onKeyEvent {
                        if (it.nativeKeyEvent.isCanceled) {
                            Timber.d("onKeyEvent: Canceled")
                            true
                        } else {
                            false
                        }
                    },
                onSend = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }
            )
        }
    }
}

@Preview
@Composable
fun ChatRoomPreview() {
    TukerInTheme {
        ChatRoomScreen(chatId = 1)
    }
}