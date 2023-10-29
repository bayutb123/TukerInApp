package com.bayutb123.tukerin.ui.screen.auth.firebase

data class GoogleSignInResult(
    val data: UserData?,
    val errorMessages: String?
)

data class UserData(
    val id: String,
    val email: String,
    val name: String,
    val photoUrl: String
)
