package com.bayutb123.tukerin.core.okhttp

/**
 * Supplies the logged-in user's API token to [interceptor.AuthInterceptor].
 *
 * The token is stored by the app module, which `:core` can't depend on, so the
 * app module provides the implementation through Hilt.
 */
fun interface TokenProvider {
    /** The saved API token, or `null` when no user is logged in. */
    suspend fun getToken(): String?
}
