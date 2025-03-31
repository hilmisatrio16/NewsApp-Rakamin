package com.rakamin.newsapp.model

import com.google.gson.annotations.SerializedName

data class DataArticle(
    val author: String,
    val publishedAt: String,
    val sourceName: String,
    val title: String,
    val url: String,
    val urlToImage: String
)
