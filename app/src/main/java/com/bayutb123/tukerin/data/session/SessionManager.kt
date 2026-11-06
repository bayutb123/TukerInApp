package com.bayutb123.tukerin.data.session

import com.bayutb123.tukerin.core.okhttp.UnauthorizedHandler
import com.bayutb123.tukerin.domain.repository.DataStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Logs the user out when the API rejects their token, the same way the
 * Logout button does: the saved user is cleared, and [sessionExpired] tells
 * the UI to go to the login screen.
 */
@Singleton
class SessionManager @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
) : UnauthorizedHandler {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val mutex = Mutex()

    // Conflated so the event waits for the UI if nothing is collecting yet,
    // and several rejected requests still produce a single event.
    private val expiredEvents = Channel<Unit>(Channel.CONFLATED)

    /** Emits when the session has expired and the user has been logged out. */
    val sessionExpired: Flow<Unit> = expiredEvents.receiveAsFlow()

    override fun onUnauthorized(rejectedToken: String) {
        scope.launch { expireSession(rejectedToken) }
    }

    /**
     * Logs the user out if [rejectedToken] is still the saved token.
     *
     * If it isn't, the user has already been logged out, or has logged in
     * again since the request was sent, and a late response for the old
     * session must not log them out.
     */
    suspend fun expireSession(rejectedToken: String) {
        mutex.withLock {
            if (dataStoreRepository.getToken() != rejectedToken) return
            dataStoreRepository.clearUser()
            expiredEvents.trySend(Unit)
        }
    }
}
