package com.rakamin.newsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rakamin.newsapp.data.remote.response.NewsResponse
import com.rakamin.newsapp.data.remote.response.Resource
import com.rakamin.newsapp.data.repositories.RemoteRepository
import com.rakamin.newsapp.utils.ConstantValues
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {

    private val _dataAllNews = MutableStateFlow<Resource<NewsResponse>?>(null)
    val dataAllNews = _dataAllNews.asStateFlow()

    private val _dataHeadlineNews = MutableStateFlow<Resource<NewsResponse>?>(null)
    val dataHeadlineNews = _dataHeadlineNews.asStateFlow()

    fun getAllNews(q: String, apiKey: String, pageSize: Int) {
        viewModelScope.launch {
            repository.getAllNews(
                q,
                apiKey,
                pageSize
            ).collect { result ->
                _dataAllNews.value = result
            }
        }
    }

    fun getHeadlines(country: String, apiKey: String) {
        viewModelScope.launch {
            repository.getHeadlineNews(country, apiKey).collect { result ->
                _dataHeadlineNews.value = result
            }
        }
    }
}