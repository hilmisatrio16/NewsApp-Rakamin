package com.rakamin.newsapp.di

import com.rakamin.newsapp.data.repositories.RemoteRepository
import com.rakamin.newsapp.data.repositories.RemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsRemoteRepository(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository
}