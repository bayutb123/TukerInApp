package com.bayutb123.tukerin.ui.screen.auth.register

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.components.input.CustomTextField
import com.bayutb123.tukerin.ui.components.input.FullWidthButton
import com.bayutb123.tukerin.ui.components.view.CustomAlertDialog
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import com.bayutb123.tukerin.core.utils.InputValidation

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit,
    onBackRequested: () -> Unit
) {
    val viewModel: RegisterViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    if (state.value is RegisterState.Success) {
        // TODO: Redirect to home or login page
        errorMsg = (state.value as RegisterState.Success).msg.toString()
    } else if (state.value is RegisterState.Error) {
        errorMsg = ((state.value as RegisterState.Error).errorMsg)
    }

    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
//                .background(Backgrounds.largeRadialGradient)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(visible = state.value is RegisterState.Error || state.value is RegisterState.Success) {
                CustomAlertDialog(
                    title = "Notice",
                    message = errorMsg,
                    dismissEnabled = false,
                    onConfirm = {
                        if (state.value is RegisterState.Success) {
                            onNavigationRequested(Screen.Login.route)
                        } else {
                            viewModel.resetState()
                        }
                    }
                )
            }
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
            ) {
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = "Welcome to", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "TukerIn",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    onTextChanged = { name = it },
                    placeholder = "Name",
                    keyboardType = KeyboardType.Text,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Name"
                        )
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    onTextChanged = { email = it },
                    placeholder = "Email",
                    isError = !InputValidation.validateEmailInput(email),
                    errorMsg = "Email is not valid",
                    keyboardType = KeyboardType.Email,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    },
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    onTextChanged = { password = it },
                    placeholder = "Password",
                    isError = !InputValidation.validatePasswordInput(password),
                    errorMsg = "Password must be at least 8 characters",
                    keyboardType = KeyboardType.Password,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Password"
                        )
                    },
                    isHidden = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    onTextChanged = { confirmPassword = it },
                    placeholder = "Confirm Password",
                    isError = password != confirmPassword,
                    errorMsg = "Password are not match",
                    keyboardType = KeyboardType.Password,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Confirm Password"
                        )
                    },
                    isHidden = true
                )
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    FullWidthButton(onClick = {
                        viewModel.register(name, email, password, confirmPassword)
                    }, text = "Register")
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = "Already have an account?",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        TextButton(onClick = {
                            onBackRequested()
                        }) {
                            Text(
                                text = "Login",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = modifier.padding(start = 8.dp),
                                fontWeight = FontWeight.Bold
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun RegisterScreenPreview() {
    TukerInTheme {
        RegisterScreen(
            onNavigationRequested = {},
            onBackRequested = {}
        )
    }
}