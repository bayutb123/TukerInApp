package com.bayutb123.tukerin.ui.screen.auth.login

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
import com.bayutb123.tukerin.ui.components.view.AlertDialogWithNoCancel
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import com.bayutb123.tukerin.ui.utils.InputValidation

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginAuthorized: (String) -> Unit,
    onNavigationRequested: (route: String) -> Unit
) {
    val viewModel : LoginViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var errorMsg by rememberSaveable { mutableStateOf("") }

    if (state.value is LoginState.Success) {
        onLoginAuthorized(Screen.Home.route)
    } else if (state.value is LoginState.Error) {
        errorMsg = state.value.message ?: ""
    }

    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
//                .background(Backgrounds.largeRadialGradient)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(visible = state.value is LoginState.Error) {
                AlertDialogWithNoCancel(
                    title = "Login Failed",
                    message = errorMsg,
                    onDismiss = { viewModel.resetState() },
                    onConfirm = { viewModel.resetState() }
                )
            }
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "Login to continue", style = MaterialTheme.typography.titleMedium)
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
                Column {
                    FullWidthButton(onClick = {
                        viewModel.login(
                            email = email,
                            password = password
                        )
                    }, text = "Login", enabled = state.value !is LoginState.Loading)
                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        TextButton(onClick = {
                            onNavigationRequested(Screen.Forgot.route)
                        }) {
                            Text(
                                text = "Forgot Password?",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        TextButton(onClick = {
                            onNavigationRequested(Screen.Register.route)
                        }) {
                            Text(text = "Register", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    TukerInTheme {
        LoginScreen(
            onLoginAuthorized = {},
            onNavigationRequested = { },
        )
    }
}