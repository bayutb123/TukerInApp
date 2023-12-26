package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: String,
    isSender: Boolean,
    isRead: Boolean,
) {
    val senderPaddingEnd = if (!isSender) {
        16.dp
    } else {
        0.dp
    }
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = if (isSender) {
            Alignment.CenterEnd
        } else {
            Alignment.CenterStart
        }
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(end = senderPaddingEnd, start = senderPaddingEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (!isSender) {
                AsyncImage(
                    model = "https://static-00.iconduck.com/assets.00/user-icon-2048x2048-ihoxz4vq.png",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(50.dp)
                        .background(color = Color.Transparent, shape = CircleShape),
                )
            }
            Row(
                modifier = Modifier
                    .background(
                        color = if (isSender) {
                            Color(0xFFE0E0E0)
                        } else {
                            Color(0xFFBDBDBD)
                        },
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(text = message)
            }
        }
    }
}

@Preview
@Composable
fun ChatBubblePreview() {
    TukerInTheme {
        Column {
            ChatBubble(message = "Hello", isSender = true, isRead = true)
            ChatBubble(
                message = "trxId value can be obtained from each transaction history at transaction list. Example: 1282556767 \n" +
                        "\n" +
                        "trxType value can be obtained from each transaction history at transaction list. Example: RECHARGE ",
                isSender = false,
                isRead = true
            )
            ChatBubble(message = "Hello", isSender = true, isRead = true)
            ChatBubble(message = "Hello", isSender = true, isRead = true)
        }
    }
}