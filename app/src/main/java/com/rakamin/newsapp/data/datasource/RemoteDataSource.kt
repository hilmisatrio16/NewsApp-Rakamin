package com.rakamin.newsapp.data.datasource

import com.rakamin.newsapp.data.remote.response.NewsResponse
import com.rakamin.newsapp.data.remote.response.Resource
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getAllNews(q: String, apiKey: String, pageSize: Int): Flow<Resource<NewsResponse>>
    fun getHeadlineNews(country: String, apiKey: String): Flow<Resource<NewsResponse>>
}