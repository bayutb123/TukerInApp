package com.bayutb123.tukerin.ui.components.input

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb123.tukerin.ui.theme.TukerInTheme


@Composable
fun FullWidthButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        shape = RoundedCornerShape(MaterialTheme.shapes.medium.topStart),
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        // while loading the button is busy, not unavailable: keep its normal
        // colors so the spinner stays visible
        colors = if (isLoading) {
            color.copy(
                disabledContainerColor = color.containerColor,
                disabledContentColor = color.contentColor,
            )
        } else {
            color
        },
        enabled = enabled && !isLoading
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            contentAlignment = Alignment.Center,
        ) {
            // keep the text laid out (invisible) so the button keeps its height
            Text(
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier.alpha(if (isLoading) 0f else 1f),
            )
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = LocalContentColor.current,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewFullWidthButton() {
    TukerInTheme {
        Column {
            FullWidthButton(onClick = { /*TODO*/ }, text = "Login")
            Spacer(modifier = Modifier.height(8.dp))
            FullWidthButton(onClick = { /*TODO*/ }, text = "Login", isLoading = true)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                FullWidthButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }, text = "Test")
                Spacer(modifier = Modifier.width(8.dp))
                FullWidthButton(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }, text = "Test")
            }
        }
    }
}