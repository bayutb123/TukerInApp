package com.bayutb123.tukerin.core.okhttp.interceptor

import com.bayutb123.tukerin.core.okhttp.TokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds the logged-in user's API token to every request as
 * `Authorization: Bearer {token}`. The API requires it on every endpoint
 * except login and register.
 *
 * When no user is logged in, requests are sent unchanged.
 */
class AuthInterceptor(
    private val tokenProvider: TokenProvider
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // OkHttp calls interceptors on its own background threads, so blocking
        // here is safe. DataStore keeps the preferences in memory after the
        // first read, so this doesn't hit the disk on every request.
        val token = runBlocking { tokenProvider.getToken() }

        val request = if (token.isNullOrBlank()) {
            chain.request()
        } else {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}
