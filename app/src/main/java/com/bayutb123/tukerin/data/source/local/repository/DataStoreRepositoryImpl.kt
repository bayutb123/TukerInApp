package com.bayutb123.tukerin.data.source.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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

    companion object {
        val TOKEN = stringPreferencesKey("token")
        val ID = intPreferencesKey("id")
    }
}