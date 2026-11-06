package com.bayutb123.tukerin.core.okhttp

/**
 * Told by [interceptor.AuthInterceptor] when the API rejects the user's token
 * with `401 Unauthorized`.
 *
 * The app module implements it to log the user out, since `:core` can't reach
 * the app's session or navigation.
 */
fun interface UnauthorizedHandler {
    /**
     * Called on an OkHttp background thread, so implementations must not
     * block or touch the UI.
     *
     * @param rejectedToken the token that was sent with the rejected request.
     */
    fun onUnauthorized(rejectedToken: String)
}
