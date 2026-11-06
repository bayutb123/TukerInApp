package com.bayutb123.tukerin.ui.components.input

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb123.tukerin.ui.theme.TukerInTheme

/**
 * Text field for the login and register forms.
 *
 * Unlike [CustomTextField] it keeps no text of its own: the caller owns
 * [value], so what is typed survives recomposition and configuration changes.
 *
 * @param errorMessage shown below the field, which is then marked as invalid.
 * @param supportingText hint shown below the field while there is no error.
 * @param isPassword hides the text and adds a show/hide toggle.
 * @param onImeAction called when the keyboard's action key is pressed with
 *  [ImeAction.Done]; with [ImeAction.Next] focus moves to the next field.
 * @param autofill lets password managers and autofill services fill the field.
 */
@Composable
fun AuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: ImageVector,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.None,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    isPassword: Boolean = false,
    errorMessage: String? = null,
    supportingText: String? = null,
    autofill: ContentType? = null,
    enabled: Boolean = true,
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    val isError = errorMessage != null
    val helper = errorMessage ?: supportingText

    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .then(
                if (autofill != null) Modifier.semantics { contentType = autofill } else Modifier
            ),
        enabled = enabled,
        singleLine = true,
        label = { Text(text = label) },
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        trailingIcon = if (isPassword) {
            {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) {
                            Icons.Outlined.VisibilityOff
                        } else {
                            Icons.Outlined.Visibility
                        },
                        contentDescription = if (passwordVisible) "Hide password" else "Show password",
                    )
                }
            }
        } else {
            null
        },
        isError = isError,
        supportingText = helper?.let { { Text(text = it) } },
        visualTransformation = if (isPassword && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            capitalization = capitalization,
            autoCorrectEnabled = !isPassword && keyboardType == KeyboardType.Text,
            imeAction = imeAction,
        ),
        keyboardActions = if (imeAction == ImeAction.Done) {
            KeyboardActions(onDone = { onImeAction() })
        } else {
            KeyboardActions.Default
        },
        shape = RoundedCornerShape(MaterialTheme.shapes.medium.topStart),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            disabledContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
            errorContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            // the show/hide toggle is a control, not part of the error
            errorTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
    )
}

@Preview(showBackground = true)
@Composable
private fun AuthTextFieldPreview() {
    TukerInTheme {
        Surface(color = MaterialTheme.colorScheme.primaryContainer) {
            Column {
                AuthTextField(
                    value = "budi@example.com",
                    onValueChange = {},
                    label = "Email",
                    leadingIcon = Icons.Outlined.Email,
                )
                Spacer(modifier = Modifier.height(8.dp))
                AuthTextField(
                    value = "budi@",
                    onValueChange = {},
                    label = "Email",
                    leadingIcon = Icons.Outlined.Email,
                    errorMessage = "Enter a valid email address",
                )
                Spacer(modifier = Modifier.height(8.dp))
                AuthTextField(
                    value = "secret123",
                    onValueChange = {},
                    label = "Password",
                    leadingIcon = Icons.Outlined.Lock,
                    isPassword = true,
                    supportingText = "At least 8 characters",
                )
            }
        }
    }
}
