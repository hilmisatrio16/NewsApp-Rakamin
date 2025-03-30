package com.rakamin.newsapp.data.repositories

import com.rakamin.newsapp.data.datasource.RemoteDataSource
import com.rakamin.newsapp.data.remote.response.NewsResponse
import com.rakamin.newsapp.data.remote.response.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): RemoteRepository {
    override fun getAllNews(
        q: String,
        apiKey: String,
        pageSize: Int
    ): Flow<Resource<NewsResponse>> {
        return remoteDataSource.getAllNews(q,apiKey,pageSize)
    }

    override fun getHeadlineNews(country: String, apiKey: String): Flow<Resource<NewsResponse>> {
        return remoteDataSource.getHeadlineNews(country,apiKey)
    }
}