package com.bayutb123.tukerin.ui

import androidx.lifecycle.ViewModel
import com.bayutb123.tukerin.data.session.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** Exposes [SessionManager.sessionExpired] to [App]. */
@HiltViewModel
class SessionViewModel @Inject constructor(
    sessionManager: SessionManager,
) : ViewModel() {
    val sessionExpired: Flow<Unit> = sessionManager.sessionExpired
}
