package com.example.newsapp.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.db.database.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.retrofit.model.News
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodelfactory.NewsViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var newsViewModel:NewsViewModel
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
val navHostFragment= supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.btmNav.setupWithNavController(navController)

        val articleDao = ArticleDatabase.getDataBase(application).getArticleDao()
val repository = NewsRepository(articleDao)
         val viewmodelFactory = NewsViewModelFactory(repository,application)
        newsViewModel = ViewModelProvider(this,viewmodelFactory).get(NewsViewModel::class.java)


    }
}