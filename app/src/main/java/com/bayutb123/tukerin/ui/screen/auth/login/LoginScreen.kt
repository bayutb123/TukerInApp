package com.bayutb123.tukerin.ui.screen.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bayutb123.tukerin.core.utils.InputValidation
import com.bayutb123.tukerin.ui.components.input.AuthTextField
import com.bayutb123.tukerin.ui.components.input.FullWidthButton
import com.bayutb123.tukerin.ui.components.view.AuthScaffold
import com.bayutb123.tukerin.ui.components.view.CustomAlertDialog
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginAuthorized: () -> Unit,
    onNavigationRequested: (route: String) -> Unit
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    // navigate once, not on every recomposition
    LaunchedEffect(state) {
        if (state is LoginState.Success) onLoginAuthorized()
    }

    CustomAlertDialog(
        title = "Login failed",
        message = state.message.orEmpty(),
        isVisible = state is LoginState.Error,
        dismissEnabled = false,
        onDismiss = { viewModel.resetState() },
        onConfirm = { viewModel.resetState() }
    )

    LoginContent(
        modifier = modifier,
        email = email,
        onEmailChange = { email = it.trim() },
        password = password,
        onPasswordChange = { password = it },
        isLoading = state is LoginState.Loading,
        onLogin = { viewModel.login(email = email, password = password) },
        onForgotPassword = { onNavigationRequested(Screen.Forgot.route) },
        onRegister = { onNavigationRequested(Screen.Register.route) },
    )
}

/** The login form, without the ViewModel, so it can be previewed. */
@Composable
fun LoginContent(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isLoading: Boolean,
    onLogin: () -> Unit,
    onForgotPassword: () -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val emailError = if (InputValidation.validateEmailInput(email)) {
        null
    } else {
        "Enter a valid email address"
    }
    val canSubmit = email.isNotBlank() && emailError == null && password.isNotEmpty() && !isLoading

    AuthScaffold(
        modifier = modifier,
        title = "TukerIn",
        subtitle = "Welcome back! Log in to continue.",
    ) {
        AuthTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            errorMessage = emailError,
            autofill = ContentType.EmailAddress + ContentType.Username,
            enabled = !isLoading,
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            leadingIcon = Icons.Outlined.Lock,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            onImeAction = { if (canSubmit) onLogin() },
            isPassword = true,
            autofill = ContentType.Password,
            enabled = !isLoading,
        )
        TextButton(
            onClick = onForgotPassword,
            enabled = !isLoading,
            modifier = Modifier.align(Alignment.End),
        ) {
            Text(text = "Forgot password?")
        }
        Spacer(modifier = Modifier.height(8.dp))
        FullWidthButton(
            onClick = onLogin,
            text = "Login",
            enabled = canSubmit,
            isLoading = isLoading,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
            )
            TextButton(onClick = onRegister, enabled = !isLoading) {
                Text(text = "Register", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4_XL)
@Composable
private fun LoginContentPreview() {
    TukerInTheme {
        LoginContent(
            email = "budi@example.com",
            onEmailChange = {},
            password = "secret123",
            onPasswordChange = {},
            isLoading = false,
            onLogin = {},
            onForgotPassword = {},
            onRegister = {},
        )
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun LoginContentErrorLoadingPreview() {
    TukerInTheme {
        LoginContent(
            email = "budi@",
            onEmailChange = {},
            password = "",
            onPasswordChange = {},
            isLoading = true,
            onLogin = {},
            onForgotPassword = {},
            onRegister = {},
        )
    }
}
