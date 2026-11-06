package com.bayutb123.tukerin.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bayutb123.tukerin.data.source.local.repository.DataStoreRepositoryImpl
import com.bayutb123.tukerin.core.okhttp.TokenProvider
import com.bayutb123.tukerin.domain.repository.DataStoreRepository
import com.bayutb123.tukerin.domain.usecase.DataStoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>) : DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore)
    }

    /** Lets the network layer in :core read the token for AuthInterceptor. */
    @Provides
    fun provideTokenProvider(dataStoreRepository: DataStoreRepository) : TokenProvider {
        return TokenProvider { dataStoreRepository.getToken() }
    }

    @Provides
    fun provideDataStoreUseCase(dataStoreRepository: DataStoreRepository) : DataStoreUseCase {
        return DataStoreUseCase(dataStoreRepository)
    }
}