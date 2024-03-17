package com.bayutb123.tukerin.ui.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun CustomMessageField(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    placeholder: String?,
    isError: Boolean = false,
    onSend: (String) -> Unit,
    imeAction: ImeAction = ImeAction.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    var text by remember {
        mutableStateOf("")
    }
    Column {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
                onTextChanged(it)
            },
            isError = isError,
            singleLine = singleLine,
            maxLines = maxLines,
            shape = RoundedCornerShape(50),
            colors = TextFieldDefaults.colors(
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                cursorColor = contentColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            leadingIcon = leadingIcon,
            trailingIcon = {
                IconButton(onClick = {
                    onSend(text)
                    text = ""
                }, enabled = text != "") {
                    Icon(imageVector = Icons.AutoMirrored.Default.Send, contentDescription = "Send")
                }
            },
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            placeholder = {
                if (placeholder != null) {
                    Text(text = placeholder, color = Color.Gray)
                }
            }
        )
    }
}

