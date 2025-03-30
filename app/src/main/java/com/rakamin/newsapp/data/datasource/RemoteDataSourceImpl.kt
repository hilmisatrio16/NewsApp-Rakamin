package com.rakamin.newsapp.data.datasource

import com.rakamin.newsapp.data.remote.response.NewsResponse
import com.rakamin.newsapp.data.remote.response.Resource
import com.rakamin.newsapp.data.remote.service.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
): RemoteDataSource {
    override fun getAllNews(
        q: String,
        apiKey: String,
        pageSize: Int
    ): Flow<Resource<NewsResponse>> {
        return flow {
            try {
                val response = apiServices.getAllNews(q, apiKey,pageSize)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getHeadlineNews(country: String, apiKey: String): Flow<Resource<NewsResponse>> {
        return flow {
            try {
                val response = apiServices.getHeadlineNews(country, apiKey)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}