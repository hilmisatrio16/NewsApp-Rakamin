package com.rakamin.newsapp.ui.search

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
class SearchViewModel @Inject constructor(
    private val repository: RemoteRepository
) : ViewModel() {
    private val _dataSearch = MutableStateFlow<Resource<NewsResponse>?>(null)
    val dataSearch = _dataSearch.asStateFlow()

    private val _dataNews = MutableStateFlow<Resource<NewsResponse>?>(null)
    val dataNews = _dataNews.asStateFlow()


    fun searchNews(q: String, apiKey: String, pageSize: Int) {
        viewModelScope.launch {
            repository.getAllNews(
                q,
                apiKey,
                pageSize
            ).collect { result ->
                _dataSearch.value = result
            }
        }
    }

    fun getNews(q: String = "", country: String = "", apiKey: String, pageSize: Int, mode: String) {
        viewModelScope.launch {
            if (mode === ConstantValues.ALLNEWS) {
                repository.getAllNews(
                    q,
                    apiKey,
                    pageSize
                ).collect { result ->
                    _dataNews.value = result
                }
            } else {
                repository.getHeadlineNews(country, apiKey).collect { result ->
                    _dataNews.value = result
                }
            }
        }
    }
}