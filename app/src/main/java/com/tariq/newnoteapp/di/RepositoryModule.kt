package com.tariq.newnoteapp.di

import android.content.Context
import com.tariq.newnoteapp.data.repository.AuthRepository
import com.tariq.newnoteapp.data.repository.RemoteDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(): AuthRepository {
        return AuthRepository()
    }

    @Provides
    @Singleton
    fun provideRemoteDataRepository():
            RemoteDataRepository = RemoteDataRepository()
}