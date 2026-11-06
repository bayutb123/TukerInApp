package com.bayutb123.tukerin.core.okhttp.interceptor

import com.bayutb123.tukerin.core.okhttp.TokenProvider
import com.bayutb123.tukerin.core.okhttp.UnauthorizedHandler
import com.bayutb123.tukerin.core.utils.ResponseCode
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds the logged-in user's API token to every request as
 * `Authorization: Bearer {token}`. The API requires it on every endpoint
 * except login and register.
 *
 * When the API answers a request that carried the token with
 * `401 Unauthorized`, the token is no longer valid, and [unauthorizedHandler]
 * is told so it can log the user out.
 *
 * Requests are sent unchanged when no user is logged in, and to login and
 * register, which don't take a token. A wrong password on login (also a 401)
 * therefore never counts as a rejected token.
 */
class AuthInterceptor(
    private val tokenProvider: TokenProvider,
    private val unauthorizedHandler: UnauthorizedHandler,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (isPublic(request.url.encodedPath)) {
            return chain.proceed(request)
        }

        // OkHttp calls interceptors on its own background threads, so blocking
        // here is safe. DataStore keeps the preferences in memory after the
        // first read, so this doesn't hit the disk on every request.
        val token = runBlocking { tokenProvider.getToken() }
        if (token.isNullOrBlank()) {
            return chain.proceed(request)
        }

        val response = chain.proceed(
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        )
        if (response.code == ResponseCode.UNAUTHORIZED) {
            unauthorizedHandler.onUnauthorized(token)
        }
        return response
    }

    private fun isPublic(path: String): Boolean =
        PUBLIC_PATHS.any { path.endsWith(it) }

    private companion object {
        /** Endpoints that don't take a token. */
        val PUBLIC_PATHS = listOf("/user/login", "/user/register")
    }
}
