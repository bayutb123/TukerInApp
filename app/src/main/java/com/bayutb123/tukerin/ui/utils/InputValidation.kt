package com.bayutb123.tukerin.ui.utils

class InputValidation {
    companion object {
        fun validateEmailInput(email: String): Boolean {
            return if (email.isNotEmpty()) {
                android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
            } else {
                true
            }
        }

        fun validatePasswordInput(password: String) : Boolean {
            return if (password.isNotEmpty()) {
                password.length > 7
            } else {
                true
            }
        }
    }
}