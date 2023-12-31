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
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageList(
    data: List<Message>,
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
            items(items = data, key = { item -> item.id } ){ message ->
                ListItem(
                    headlineContent = { Text(text = message.sender) },
                    leadingContent = {
                        AsyncImage(
                            model = message.attachment[0],
                            contentDescription = "",
                            modifier = Modifier
                                .background(shape = CircleShape, color = Color.Transparent)
                                .size(50.dp),
                            contentScale = ContentScale.Crop
                        )
                    },
                    supportingContent = {
                        Text(text = message.message)
                    },
                    modifier = Modifier.clickable {
                        onItemClick(message.id)
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
        MessageList(data = listOf(
            Message(
                1,
                "Post Name",
                "User Account",
                message = "Message",
                attachment = listOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQfQ-FW4almFWo8m9YPTj2JNAR2Ha82G7qC_w&usqp=CAU")
            )
        ), onItemClick = {

        })
    }
}