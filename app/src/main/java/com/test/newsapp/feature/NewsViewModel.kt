package com.test.newsapp.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.newsapp.ApiResults
import com.test.newsapp.NewsData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ViewModel for fetching article data from server
 */
class NewsViewModel : ViewModel() {

    private val newsRepo = NewsRepo()
    private val newsMutableLiveData = MutableLiveData<ApiResults<NewsData>>()
    val newsData: LiveData<ApiResults<NewsData>>
        get() = newsMutableLiveData

    /**
     * Launches coroutine for fetching article in background thread
     */
    fun getData() {
        viewModelScope.launch(Dispatchers.Default) {
            newsMutableLiveData.postValue(newsRepo.getData())
        }
    }
}