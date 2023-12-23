package com.bayutb123.tukerin.ui.components.input

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun ChipItem(
    text: String,
    shape: Shape = RoundedCornerShape(8.dp)
) {
    var contentColor: Color = MaterialTheme.colorScheme.primary
    var state by rememberSaveable { mutableStateOf(false) }
    if (state) contentColor = MaterialTheme.colorScheme.surface
    Box(
        modifier = Modifier
            .clickable {
                state = !state
            }
            .background(
                color = if (state) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.surface
                },
                shape = shape
            )
            .border(width = 1.dp, shape = shape, color = contentColor)
            .padding(4.dp)
    ) {
        Text(text = text, color = contentColor)
    }
}

@Preview
@Composable
fun PreviewChipItem() {
    TukerInTheme {
        ChipItem(text = "Premium")
    }
}