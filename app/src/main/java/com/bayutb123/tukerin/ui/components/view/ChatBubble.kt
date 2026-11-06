package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.domain.model.Message
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun ChatBubble(
    modifier: Modifier = Modifier,
    message: Message,
    userId: Int
) {
    val isSender = message.senderId == userId
    val startPadding = if (isSender) 16.dp else 0.dp
    val endPadding = if (!isSender) 16.dp else 0.dp
    val alignment: Alignment.Horizontal = if (!isSender) Alignment.Start else Alignment.End
    Box(
        modifier = modifier
            .fillMaxWidth(),
        contentAlignment = if (isSender) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .padding(start = startPadding, end = endPadding),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if (!isSender) {
                AsyncImage(
                    model = "https://static-00.iconduck.com/assets.00/user-icon-2048x2048-ihoxz4vq.png",
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .size(32.dp)
                        .background(color = Color.Transparent, shape = CircleShape),
                )
            }
            Column(
                modifier = Modifier.clickable {  }
                    .background(
                        color = if (isSender) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.tertiary
                        },
                        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalAlignment = alignment
            ) {
                Text(text = message.message, color = MaterialTheme.colorScheme.onPrimary)
                Text(
                    text = "12:00 PM",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview
@Composable
fun ChatBubblePreview() {
    TukerInTheme {
        Column {

        }
    }
}