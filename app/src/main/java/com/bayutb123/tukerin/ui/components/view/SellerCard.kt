package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerCard(
    modifier: Modifier = Modifier,
    sellerName: String,
    sellerLocation: String,
    sellerImage: String,
) {
    ElevatedCard(onClick = { /*TODO*/ }) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = sellerImage,
                contentDescription = sellerName,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .clip(CircleShape)
                    .size(64.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = modifier.weight(1f)) {
                Text(
                    modifier = modifier.fillMaxWidth(),
                    text = sellerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = sellerLocation,
                    modifier = modifier.fillMaxWidth(),
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewSellerCard() {
    TukerInTheme {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            SellerCard(
                sellerName = "John Doe",
                sellerLocation = "Jakarta",
                sellerImage = "https://imgv3.fotor.com/images/slider-image/a-man-holding-a-camera-with-image-filter.jpg"
            )
        }
    }
}