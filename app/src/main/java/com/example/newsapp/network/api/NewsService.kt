package com.example.newsapp.network.api

import com.example.newsapp.retrofit.model.News
import com.example.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsService {
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode: String = "us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<News>

@GET("/v2/everything")
suspend fun searchNews(
    @Query("q")
    searchTopic:String,
    @Query("page")
    page:Int=1,
    @Query("apiKey")
    apiKey: String = API_KEY
):Response<News>
}