package com.example.newsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsapp.db.database.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.model.News
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class NewsViewModel(private val repository:NewsRepository,application: Application): AndroidViewModel(application) {
     var getNewsResponse: MutableLiveData<Resource<News>> = MutableLiveData()
     var  searchNewsResponse:MutableLiveData<Resource<News>> = MutableLiveData()
    init {
        getNews("in")
    }

    val breakingNewsPage = 1
    val searchNewsPage = 1
    fun getNews(countrycode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getNewsResponse.postValue(Resource.Loading())
            val newsresponse = repository.getNews(countrycode, breakingNewsPage)
            withContext(Dispatchers.Main) {
                getNewsResponse.postValue(handleBreakingNewsResponse(newsresponse))
            }
        }
    }

    fun searchNews(searchTopic:String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.searchNews(searchTopic, searchNewsPage)
            withContext(Dispatchers.Main) {
                searchNewsResponse.postValue(handleSearchNewsResponse(response))
            }
        }
    }

    fun addArticle(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addArticle(article)
        }
    }

    val getSavedNews:LiveData<List<Article>> =repository.getSavedNews


    fun deleteArticle(article: Article){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteArticle(article)
        }
    }

    private fun handleBreakingNewsResponse(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let {News->
                return Resource.Sucess(News)
            }
        }
        return Resource.Failure(null, response.message())
    }

    private fun handleSearchNewsResponse(response:Response<News>):Resource<News>{
        if (response.isSuccessful){
            response.body()?.let { News->
                return Resource.Sucess(News)
            }
        }
        return Resource.Failure(null,response.message())
    }
}