package com.example.newsapp.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsapp.db.converters.Converter
import com.example.newsapp.db.dao.ArticleDao
import com.example.newsapp.retrofit.model.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converter::class)
abstract class ArticleDatabase: RoomDatabase() {
abstract fun getArticleDao():ArticleDao

companion object{
    @Volatile
    private var INSTANCE :ArticleDatabase? = null

    fun getDataBase(context: Context):ArticleDatabase{
        val tempInstance = INSTANCE

        return tempInstance?:
        synchronized(this){
            val instance = Room.databaseBuilder(context.applicationContext,
            ArticleDatabase::class.java,
                "article_database"
                ).build()
            INSTANCE = instance
            return instance
        }
    }
}
}