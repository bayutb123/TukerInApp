package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.bayutb123.tukerin.ui.components.input.FullWidthButton

@Composable
fun CustomAlertDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    title: String,
    message: String,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    confirmText: String = "OK",
    dismissEnabled: Boolean = true,
    confirmEnabled: Boolean = true,
    isDangerConfirmation: Boolean = false,
    onDismissRequest: () -> Unit = onDismiss
) {
    val color = if (isDangerConfirmation) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.primary
    }
    if (isVisible) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                if (confirmEnabled) {
                    TextButton(onClick = onConfirm) {
                        Text(text = confirmText, color = color)
                    }
                }
            },
            dismissButton = {
                if (dismissEnabled) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Cancel")
                    }
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = message)
            },
            icon = {
                Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
            },
            tonalElevation = 8.dp,
            properties = DialogProperties(
                dismissOnBackPress = dismissEnabled,
                dismissOnClickOutside = dismissEnabled
            ),
            modifier = modifier
        )
    }
}

@Preview
@Composable
fun PreviewCustomAlertDialog() {
    Column {
        CustomAlertDialog(
            title = "Invalid Credentials",
            message = "Please check your email and password",
            isVisible = true,
            onDismiss = { /*TODO*/ },
            onConfirm = { /*TODO*/ })
    }
}