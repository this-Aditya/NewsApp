package com.example.newsapp.repository

import com.example.newsapp.db.dao.ArticleDao
import com.example.newsapp.network.api.RetrofitInstance
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.retrofit.model.News
import com.example.newsapp.utils.Constants
import com.example.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response

class NewsRepository(private val articleDao: ArticleDao) {

    val getSavedNews = articleDao.getSavedNews()

    suspend fun deleteArticle(article: Article){
        articleDao.deleteArticle(article)
    }
    suspend fun addArticle(article: Article){
        articleDao.addArticle(article)
    }

    suspend fun getNews(countrycode:String,pagenumber:Int):Response<News> = RetrofitInstance.api.getBreakingNews(countrycode,pagenumber)

    suspend fun searchNews(searchTopic:String,pagenumber: Int):Response<News> = RetrofitInstance.api.searchNews(searchTopic,pagenumber)
}