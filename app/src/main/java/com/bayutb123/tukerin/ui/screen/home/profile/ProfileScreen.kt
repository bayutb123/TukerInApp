package com.bayutb123.tukerin.ui.screen.home.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bayutb123.tukerin.ui.theme.TukerInTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit,
) {
    val viewModel: ProfileViewModel = hiltViewModel()

    Scaffold { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                ProfileContent(
                    userName = "Bayu Tantra Bramandhita",
                    transactionPoint = 1000,
                    rating = 5,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                        )
                        .padding(16.dp)
                ) {
                    Column {
                        ListItem(
                            headlineContent = { Text(text = "Edit profile") },
                            supportingContent = { Text(text = "Edit your profile") })
                        HorizontalDivider()
                        ListItem(
                            headlineContent = { Text(text = "Settings") },
                            supportingContent = { Text(text = "TukerIn app settings") })
                        HorizontalDivider()
                        ListItem(
                            headlineContent = { Text(text = "Logout") },
                            supportingContent = { Text(text = "Logout from user Bayu Tri") },
                            modifier = Modifier.clickable {
                                CoroutineScope(Dispatchers.IO).launch {
                                    viewModel.logout()
                                }
                                onLogout()
                            })
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileContent(
    userName: String,
    transactionPoint: Int,
    rating: Int,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .size(200.dp)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Welcome Back,",
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = userName,
            color = contentColor,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Transaction point: $transactionPoint",
            color = contentColor,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Rating", color = contentColor, style = MaterialTheme.typography.bodyMedium)
            Row() {
                // Rating stars
                repeat(rating) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "star",
                        tint = Color.Yellow
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4_XL)
@Composable
fun ProfileScreenPreview() {
    TukerInTheme {
        ProfileScreen(onLogout = {})
    }
}