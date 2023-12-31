package com.bayutb123.tukerin.ui.components.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.bayutb123.tukerin.ui.components.input.FullWidthButton

@Composable
fun CustomAlertDialog(
    title: String,
    message: String,
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {},
    confirmText: String = "OK",
    dismissEnabled: Boolean = true,
    confirmEnabled: Boolean = true,
    onDismissRequest: () -> Unit = onDismiss,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            if (confirmEnabled) {
                FullWidthButton(onClick = onConfirm, text = confirmText)
            }
        },
        dismissButton = {
            if (dismissEnabled) {
                FullWidthButton(onClick = onDismiss, text = "Cancel")
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

@Preview
@Composable
fun PreviewCustomAlertDialog() {
    Column {
        CustomAlertDialog(
            title = "Invalid Credentials",
            message = "Please check your email and password",
            onDismiss = { /*TODO*/ },
            onConfirm = { /*TODO*/ })
    }
}