package com.example.newsapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentArticleNewsBinding
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.views.MainActivity
import com.google.android.material.snackbar.Snackbar

class ArticleNewsFragment : Fragment(R.layout.fragment_breaking_news) {
    lateinit var binding: FragmentArticleNewsBinding
    lateinit var viewModel: NewsViewModel
val args:ArticleNewsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArticleNewsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).newsViewModel
        binding.Webview.apply {
            loadUrl(args.article.url)

        }
        binding.fabSave.setOnClickListener {
            Snackbar.make(view,"Article Saved Successfully ",Snackbar.LENGTH_SHORT).show()
            viewModel.addArticle(args.article)
        }
    }


}
//override fun onCreateView(
//    inflater: LayoutInflater, container: ViewGroup?,
//    savedInstanceState: Bundle?
//): View? {
//    // Inflate the layout for this fragment
//    return inflater.inflate(R.layout.fragment_breaking_news, container, false)
//}