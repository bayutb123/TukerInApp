package com.bayutb123.tukerin.ui.components.input

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun RatingInput(
    modifier: Modifier = Modifier,
    onChange: (Int) -> Unit
) {
    var rating by rememberSaveable {
        mutableIntStateOf(0)
    }
    Box(modifier = Modifier.padding(8.dp)) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            items(5) {
                IconButton(onClick = {
                    rating = it + 1
                    onChange(rating)
                }) {
                    Icon(imageVector = Icons.Default.StarBorder, contentDescription = "StarBorder")
                }
            }
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            items(rating) {
                IconButton(onClick = {
                    rating = it + 1
                    onChange(rating)
                }) {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Star", tint = Color.Yellow)
                }
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4_XL,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
private fun RatingInputPreview() {
    TukerInTheme {
        RatingInput {
            Log.d("Rating", "Rating: $it")
        }
    }
}