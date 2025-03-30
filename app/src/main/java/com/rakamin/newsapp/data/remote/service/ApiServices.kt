package com.rakamin.newsapp.data.remote.service

import com.rakamin.newsapp.data.remote.response.NewsResponse
import com.rakamin.newsapp.utils.ConstantValues
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("everything")
    suspend fun getAllNews(
        @Query("q") q : String,
        @Query("apiKey") apiKey : String,
        @Query("pageSize") pageSize : Int
    ): NewsResponse

    @GET("top-headlines")
    suspend fun getHeadlineNews(
        @Query("country") country : String,
        @Query("apiKey") apiKey : String
    ): NewsResponse
}