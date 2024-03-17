package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FullImageView(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onDismissRequest: () -> Unit
) {

    BasicAlertDialog(onDismissRequest = onDismissRequest) {
        ZoomableImage(model = imageUrl, modifier = modifier)
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, showSystemUi = true)
fun FullImageViewPreview() {
    TukerInTheme {
        FullImageView(imageUrl = "https://manybackgrounds.com/images/hd/clear-blue-marble-high-resolution-8m6wazeb8yj2etn4.webp", onDismissRequest = { })
    }
}