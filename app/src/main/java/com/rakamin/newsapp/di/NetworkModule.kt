package com.rakamin.newsapp.di

import com.rakamin.newsapp.BuildConfig
import com.rakamin.newsapp.data.remote.service.ApiServices
import com.rakamin.newsapp.utils.ConstantValues
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(level)

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(ConstantValues.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit): ApiServices {
        return retrofit.create(ApiServices::class.java)
    }
}