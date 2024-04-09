package com.bayutb123.tukerin.data.source.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.bayutb123.tukerin.domain.model.User
import com.bayutb123.tukerin.domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {
    override suspend fun saveUser(user: User) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = user.token
            preferences[ID] = user.id
            preferences[NAME] = user.name
            preferences[EMAIL] = user.email
            preferences[TRX_POINT] = user.trxPoints
            preferences[RATING] = user.rating
            preferences[IS_PREMIUM] = user.isPremium
        }
    }

    override suspend fun clearUser() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    override suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[TOKEN]
    }

    override suspend fun getUserId(): Int? {
        val preferences = dataStore.data.first()
        return preferences[ID]
    }

    override suspend fun getUser(): User? {
        val preferences = dataStore.data.first()
        return preferences[NAME]?.let { name ->
            User(
                token = preferences[TOKEN] ?: "",
                id = preferences[ID] ?: 0,
                name = name,
                email = preferences[EMAIL] ?: "",
                trxPoints = preferences[TRX_POINT] ?: 0,
                rating = preferences[RATING] ?: 0,
                isPremium = preferences[IS_PREMIUM] ?: false
            )
        }
    }

    companion object {
        val TOKEN = stringPreferencesKey("token")
        val ID = intPreferencesKey("id")
        val NAME = stringPreferencesKey("name")
        val EMAIL = stringPreferencesKey("email")
        val TRX_POINT = intPreferencesKey("trxpoint")
        val RATING = intPreferencesKey("rating")
        val IS_PREMIUM = booleanPreferencesKey("is_premium")
    }
}