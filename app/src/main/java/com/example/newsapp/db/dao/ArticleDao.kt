package com.example.newsapp.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.retrofit.model.Article

@Dao
interface ArticleDao {

    @Query("select * from article_table")
    fun getSavedNews():LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)
}