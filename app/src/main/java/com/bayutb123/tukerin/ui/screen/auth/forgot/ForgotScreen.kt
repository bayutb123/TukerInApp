package com.bayutb123.tukerin.ui.screen.auth.forgot

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bayutb123.tukerin.ui.components.input.CustomTextField
import com.bayutb123.tukerin.ui.components.input.FullWidthButton
import com.bayutb123.tukerin.ui.components.view.CustomAlertDialog
import com.bayutb123.tukerin.ui.components.view.Backgrounds
import com.bayutb123.tukerin.ui.screen.Screen
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotScreen(
    modifier: Modifier = Modifier,
    onNavigationRequested: (route: String) -> Unit,
    onBackRequested: () -> Unit,
    viewModel: ForgotViewModel = hiltViewModel()
) {
    var email by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Forgot Password") }, navigationIcon = {
                IconButton(onClick = {
                    onBackRequested()
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Backgrounds.largeRadialGradient)
                .padding(paddingValues),
            contentAlignment = Alignment.TopCenter
        ) {
            AlertDialog(viewModel, onNavigationRequested)
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
            ) {
                Text(
                    text = "Enter your registered email",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                CustomTextField(
                    onTextChanged = { email = it },
                    placeholder = "Email",
                    keyboardType = KeyboardType.Email,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email"
                        )
                    },
                )

                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    FullWidthButton(
                        onClick = { viewModel.forgotPassword(email) },
                        text = "Confirm"
                    )
                }
            }
        }
    }
}

@Composable
fun AlertDialog(viewModel: ForgotViewModel, onNavigationRequested: (route: String) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var alertTitle by remember { mutableStateOf("") }
    var alertMsg by remember { mutableStateOf("") }
    var isAlertConfirmEnabled by remember { mutableStateOf(false) }
    var isAlertVisible by remember { mutableStateOf(false) }
    AnimatedVisibility(
        visible = isAlertVisible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        CustomAlertDialog(
            title = alertTitle,
            message = alertMsg,
            dismissEnabled = false,
            confirmEnabled = isAlertConfirmEnabled,
            onConfirm = {
                viewModel.setIdle()
                onNavigationRequested(Screen.Login.route)
            })
    }
    when (state) {
        is ForgotState.Success -> {
            isAlertConfirmEnabled = true
            isAlertVisible = true
            val data = (state as ForgotState.Success).data
            alertTitle = "Success"
            alertMsg = data
        }

        is ForgotState.Error -> {
            isAlertConfirmEnabled = true
            isAlertVisible = true
            val message = (state as ForgotState.Error).message
            alertTitle = "Error"
            alertMsg = message
        }

        is ForgotState.Loading -> {
            isAlertConfirmEnabled = false
            isAlertVisible = true
            alertTitle = "Loading"
            alertMsg = "Please wait"
        }

        else -> {
            isAlertConfirmEnabled = false
            isAlertVisible = false
        }
    }
}

@Preview
@Composable
fun ForgotScreenPreview() {
    TukerInTheme {
        ForgotScreen(
            onNavigationRequested = {},
            onBackRequested = {}
        )
    }
}