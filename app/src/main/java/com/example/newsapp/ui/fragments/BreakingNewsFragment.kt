package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.adapter.NewsAdapter
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.retrofit.model.Article
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.views.MainActivity
import com.example.newsapp.utils.Resource

class BreakingNewsFragment : Fragment() {
    lateinit var adapter: NewsAdapter
    lateinit var binding: FragmentBreakingNewsBinding
    lateinit var viewModel: NewsViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBreakingNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).newsViewModel
        adapter = NewsAdapter(object : NewsAdapter.OnClickListened {
            override fun itemClicked(position: Int) {
                val article = adapter.differ.currentList[position]
                val action = BreakingNewsFragmentDirections.actionBreakingNewsFragmentToArticleNewsFragment(article)
                view.findNavController().navigate(action)
            }
        })
        binding.rvbreakingNews.adapter = adapter
        binding.rvbreakingNews.layoutManager = LinearLayoutManager(this.context)
        viewModel.getNewsResponse.observe(viewLifecycleOwner, Observer { resource ->
            when (resource) {
                is Resource.Sucess -> {
                    hideProgressBar()
                    resource.data?.let { newsResponse ->
                        adapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Failure -> {
                    hideProgressBar()
                    resource.message?.let {
                        Log.i("BreakingNews", "message : $it ")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })


    }

    private fun showProgressBar() {
        binding.pbBreakingNews.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.pbBreakingNews.visibility = View.INVISIBLE
    }


}
