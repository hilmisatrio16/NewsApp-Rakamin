package com.rakamin.newsapp.di

import com.rakamin.newsapp.data.datasource.RemoteDataSource
import com.rakamin.newsapp.data.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindsRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

}