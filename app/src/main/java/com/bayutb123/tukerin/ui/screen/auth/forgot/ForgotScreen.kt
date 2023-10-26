package com.bayutb123.tukerin.ui.screen.auth.forgot

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bayutb123.tukerin.ui.components.input.CustomTextField
import com.bayutb123.tukerin.ui.components.input.FullWidthButton
import com.bayutb123.tukerin.ui.components.view.AlertDialogWithNoCancel
import com.bayutb123.tukerin.ui.components.view.Backgrounds
import com.bayutb123.tukerin.ui.theme.TukerInTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotScreen(
    modifier: Modifier = Modifier
) {
    var isAlertVisible by rememberSaveable { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Forgot Password") }, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
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
            AnimatedVisibility(visible = isAlertVisible) {
                AlertDialogWithNoCancel(
                    title = "Password sent",
                    message = "Please check your email",
                    onDismiss = { isAlertVisible = !isAlertVisible },
                    onConfirm = { isAlertVisible = !isAlertVisible })
            }
            Column(
                modifier = modifier.padding(horizontal = 16.dp),
            ) {
                Text(text = "Enter your registered email", style = MaterialTheme.typography.titleMedium)
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
                    FullWidthButton(onClick = { isAlertVisible = !isAlertVisible }, text = "Confirm")
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgotScreenPreview() {
    TukerInTheme {
        ForgotScreen()
    }
}