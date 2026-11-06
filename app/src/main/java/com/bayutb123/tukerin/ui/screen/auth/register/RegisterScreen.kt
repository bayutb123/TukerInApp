package com.bayutb123.tukerin.ui.screen.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
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

/** The API accepts at most 14 characters; fewer than 10 digits isn't a real number. */
private const val PHONE_MIN_DIGITS = 10
private const val PHONE_MAX_DIGITS = 14

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onRegisterSuccess: (route: String) -> Unit,
    onBackRequested: () -> Unit
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }

    CustomAlertDialog(
        title = if (state is RegisterState.Success) "Account created" else "Registration failed",
        message = state.message.orEmpty(),
        isVisible = state is RegisterState.Success || state is RegisterState.Error,
        dismissEnabled = false,
        onConfirm = {
            if (state is RegisterState.Success) {
                onRegisterSuccess(Screen.Login.route)
            } else {
                viewModel.resetState()
            }
        }
    )

    RegisterContent(
        modifier = modifier,
        name = name,
        onNameChange = { name = it },
        email = email,
        onEmailChange = { email = it.trim() },
        phone = phone,
        onPhoneChange = { input -> phone = input.filter { it.isDigit() }.take(PHONE_MAX_DIGITS) },
        password = password,
        onPasswordChange = { password = it },
        confirmPassword = confirmPassword,
        onConfirmPasswordChange = { confirmPassword = it },
        isLoading = state is RegisterState.Loading,
        onRegister = {
            viewModel.register(name.trim(), email, password, confirmPassword, phone)
        },
        onLogin = onBackRequested,
    )
}

/** The register form, without the ViewModel, so it can be previewed. */
@Composable
fun RegisterContent(
    name: String,
    onNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    isLoading: Boolean,
    onRegister: () -> Unit,
    onLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    // errors only appear once the user has typed something into the field
    val emailError = if (InputValidation.validateEmailInput(email)) {
        null
    } else {
        "Enter a valid email address"
    }
    val phoneError = if (phone.isNotEmpty() && phone.length < PHONE_MIN_DIGITS) {
        "Phone number must be at least $PHONE_MIN_DIGITS digits"
    } else {
        null
    }
    val passwordError = if (InputValidation.validatePasswordInput(password)) {
        null
    } else {
        "Password must be at least 8 characters"
    }
    val confirmError = if (confirmPassword.isNotEmpty() && confirmPassword != password) {
        "Passwords don't match"
    } else {
        null
    }
    val canSubmit = !isLoading &&
        name.isNotBlank() &&
        email.isNotBlank() && emailError == null &&
        phone.isNotEmpty() && phoneError == null &&
        password.isNotEmpty() && passwordError == null &&
        confirmPassword == password

    AuthScaffold(
        modifier = modifier,
        title = "Create account",
        subtitle = "Join TukerIn to start trading.",
        mirrored = true,
    ) {
        AuthTextField(
            value = name,
            onValueChange = onNameChange,
            label = "Full name",
            leadingIcon = Icons.Outlined.Person,
            capitalization = KeyboardCapitalization.Words,
            autofill = ContentType.PersonFullName,
            enabled = !isLoading,
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthTextField(
            value = email,
            onValueChange = onEmailChange,
            label = "Email",
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email,
            errorMessage = emailError,
            autofill = ContentType.EmailAddress,
            enabled = !isLoading,
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthTextField(
            value = phone,
            onValueChange = onPhoneChange,
            label = "Phone number",
            leadingIcon = Icons.Outlined.Phone,
            keyboardType = KeyboardType.Phone,
            errorMessage = phoneError,
            supportingText = "Used by buyers to contact you on WhatsApp",
            autofill = ContentType.PhoneNumber,
            enabled = !isLoading,
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = "Password",
            leadingIcon = Icons.Outlined.Lock,
            keyboardType = KeyboardType.Password,
            isPassword = true,
            errorMessage = passwordError,
            supportingText = "At least 8 characters",
            autofill = ContentType.NewPassword,
            enabled = !isLoading,
        )
        Spacer(modifier = Modifier.height(12.dp))
        AuthTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            label = "Confirm password",
            leadingIcon = Icons.Outlined.Lock,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
            onImeAction = { if (canSubmit) onRegister() },
            isPassword = true,
            errorMessage = confirmError,
            autofill = ContentType.NewPassword,
            enabled = !isLoading,
        )
        Spacer(modifier = Modifier.height(24.dp))
        FullWidthButton(
            onClick = onRegister,
            text = "Register",
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
                text = "Already have an account?",
                style = MaterialTheme.typography.bodyMedium,
            )
            TextButton(onClick = onLogin, enabled = !isLoading) {
                Text(text = "Login", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(device = Devices.PIXEL_4_XL)
@Composable
private fun RegisterContentPreview() {
    TukerInTheme {
        RegisterContent(
            name = "Budi Santoso",
            onNameChange = {},
            email = "budi@example.com",
            onEmailChange = {},
            phone = "081234567890",
            onPhoneChange = {},
            password = "secret123",
            onPasswordChange = {},
            confirmPassword = "secret123",
            onConfirmPasswordChange = {},
            isLoading = false,
            onRegister = {},
            onLogin = {},
        )
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
private fun RegisterContentErrorsPreview() {
    TukerInTheme {
        RegisterContent(
            name = "Budi",
            onNameChange = {},
            email = "budi@",
            onEmailChange = {},
            phone = "0812",
            onPhoneChange = {},
            password = "abc",
            onPasswordChange = {},
            confirmPassword = "abd",
            onConfirmPasswordChange = {},
            isLoading = false,
            onRegister = {},
            onLogin = {},
        )
    }
}
