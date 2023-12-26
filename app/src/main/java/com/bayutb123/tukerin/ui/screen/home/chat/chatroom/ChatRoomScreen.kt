package com.bayutb123.tukerin.ui.screen.home.chat.chatroom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen() {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Nama Produk") })
        }
    ) {paddingValues ->  
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            
        }
    }
}

@Preview
@Composable
fun ChatRoomPreview() { 
    TukerInTheme {
        ChatRoomScreen()
    }
}