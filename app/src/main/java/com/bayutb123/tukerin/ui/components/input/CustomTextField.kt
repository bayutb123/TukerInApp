package com.bayutb123.tukerin.ui.components.input

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb123.tukerin.core.utils.Currency
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    onTextChanged: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    minLines: Int = 1,
    maxLines: Int = 1,
    containerColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onPrimaryContainer,
    placeholder: String?,
    isHidden: Boolean = false,
    isError: Boolean = false,
    errorMsg: String = "",
    isCurrency: Boolean = false,
    isEnabled: Boolean = true,
    isHasDefault: Boolean = false,
    defaultText: String = "",
    readOnly : Boolean = false,
    readOnlyText: String? = null
) {
    var text by remember {
        mutableStateOf(if (isHasDefault) defaultText else "")
    }
    Column {
        TextField(
            modifier = modifier.fillMaxWidth(),
            value = if (readOnly) readOnlyText ?: text else text,
            onValueChange = {
                text = if (isCurrency) {
                    Currency.displayLongAsRupiah(it)
                } else {
                    it
                }
                onTextChanged(it)
            },
            isError = isError,
            singleLine = singleLine,
            minLines = minLines,
            maxLines = maxLines,
            shape = RoundedCornerShape(MaterialTheme.shapes.large.topStart),
            colors = TextFieldDefaults.colors(
                focusedTextColor = contentColor,
                unfocusedTextColor = contentColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = Color.Gray,
                cursorColor = contentColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledTextColor = Color.White,
                disabledPlaceholderColor = Color.White
            ),
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            placeholder = {
                if (placeholder != null) {
                    Text(text = placeholder, color = Color.Gray)
                }
            },
            visualTransformation = if (isHidden) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            enabled = isEnabled,
            readOnly = readOnly
        )
        AnimatedVisibility(visible = isError && text.isNotEmpty()) {
            Text(
                modifier = modifier.padding(start = 16.dp),
                text = errorMsg,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewCustomTextField() {
    TukerInTheme {
        var text = ""
        Column {
            CustomTextField(
                onTextChanged = { text = it },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                },
                keyboardType = KeyboardType.Number,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                placeholder = "Phone number"
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                onTextChanged = {},
                keyboardType = KeyboardType.Email,
                placeholder = "Email Address"
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                onTextChanged = {},
                trailingIcon = {
                    IconButton(
                        onClick = {},
                        content = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    )
                },
                keyboardType = KeyboardType.Email,
                placeholder = "Search..."
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                modifier = Modifier.height(102.dp),
                onTextChanged = {},
                maxLines = 3,
                keyboardType = KeyboardType.Email,
                placeholder = "Description"
            )
        }
    }
}